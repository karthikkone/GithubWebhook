package com.bpe.GithubWebhook.service;

import com.google.gson.Gson;
import com.webhook.model.Commit;
import com.webhook.model.PullCommit;
import com.webhook.model.PullRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class GithubLookUpServiceImpl implements GitLookUpService {
	@Autowired
    private RestTemplate restTemplate;
	
	@Value("${github.auth.token}")
	private String githubAuthToken;
    @Async
    @Override
    public CompletableFuture<List<PullCommit>> getPullCommits(PullRequest pullRequest) {


        if (pullRequest != null && pullRequest.hasCommits()) {
            String commitsApi = pullRequest.commitsOfPullRequest(); //api for commits of PullRequest

            if (commitsApi != null && !commitsApi.isEmpty()) {
                ResponseEntity<List<PullCommit>> response = restTemplate.exchange(commitsApi,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<PullCommit>>() {});

                if (response.getStatusCodeValue() == 200) {
                    List<PullCommit> clist = response.getBody();
                    clist = (clist != null ? clist : Collections.emptyList());
                    return CompletableFuture.completedFuture(clist);
                }
            }


        }

        return CompletableFuture.completedFuture(Collections.emptyList());
    }
    
    @Async
	@Override
	public CompletableFuture<List<Commit>> getCommit(PullCommit pullCommit) {
		if (pullCommit != null && pullCommit.getUrl() != null) {
			String readCommitsApi = pullCommit.getUrl();
			ResponseEntity<String> response = restTemplate.exchange(readCommitsApi,
					HttpMethod.GET,
					null,
					String.class);
			
			System.out.println("COMMITS : "+response.getBody());
			Gson gson = new Gson();
			Commit commitArr = gson.fromJson(response.getBody(), Commit.class);
			if (response.getStatusCodeValue() == 200) {
				List<Commit> commits = new ArrayList<Commit>();
				commits.add(commitArr);
				
				 commits = (commits != null ? commits : Collections.emptyList());
				 return CompletableFuture.completedFuture(commits);
			}
		}
		
		
		return CompletableFuture.completedFuture(Collections.emptyList());
	}

	@Override
	public boolean postComment(PullRequest pr, String comment) {
		
		if (pr != null && pr.get_links() != null && pr.get_links().getComments() != null) {
			String commentsApi = pr.get_links().getComments().getHref();
			
			if(commentsApi != null && !commentsApi.isEmpty()) {
				HttpHeaders headers = new HttpHeaders();
				headers.set("Authorization", "token "+githubAuthToken);
				String body = "{"+"\""+"body"+"\""+":"+"\""+comment+"\"";
				HttpEntity<String> entity = new HttpEntity<>(body,headers);
				ResponseEntity<String> response = restTemplate.exchange(commentsApi,HttpMethod.POST, entity,String.class);
				
				if (response.getStatusCode() == HttpStatus.OK) {
					System.out.println("[OK] posted comment on PullRequest : "+pr);
					return true;
				} else {
					System.out.println("[FAILED] to post comment on PullRequest : "+pr);
					return false;
				}
			}
		}
		return false;
	}
}
