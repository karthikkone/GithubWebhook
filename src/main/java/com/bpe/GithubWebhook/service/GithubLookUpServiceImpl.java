package com.bpe.GithubWebhook.service;

import com.webhook.model.PullCommit;
import com.webhook.model.PullRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class GithubLookUpServiceImpl implements GitLookUpService {

    private RestTemplate restTemplate;

    @Async
    @Override
    public CompletableFuture<List<PullCommit>> getCommits(PullRequest pullRequest) {


        if (pullRequest != null && pullRequest.hasCommits()) {
            String commitsApi = pullRequest.commitsOfPullRequest(); //api for commits of PullRequest

            if (commitsApi != null && !commitsApi.isEmpty()) {
                ResponseEntity<List<PullCommit>> response = restTemplate.exchange(commitsApi,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<PullCommit>>() {});

                if (response.getStatusCodeValue() == 200) {
                    List<PullCommit> clist = response.getBody();
                    return CompletableFuture.completedFuture(clist);
                }
            }


        }

        return CompletableFuture.completedFuture(Collections.emptyList());
    }
}
