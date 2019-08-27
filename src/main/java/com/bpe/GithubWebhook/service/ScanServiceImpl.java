package com.bpe.GithubWebhook.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.webhook.model.Commit;
import com.webhook.model.PullCommit;
import com.webhook.model.PullRequest;


@Service
public class ScanServiceImpl implements ScanService {

	@Autowired
	private GitLookUpService gitLookupService;
	
	@Override
	@Async
	public void initiate(PullRequest pr) {
		//process open pull requests
		List<Commit> commitsOfPR = new ArrayList<Commit>();
		if (pr != null && pr.getState().equals("open")) {
			//commit info associated with pull request
			try {
				List<PullCommit> pullCommits = gitLookupService.getPullCommits(pr).get();
				
				if (!pullCommits.isEmpty()) {
					for (PullCommit pc : pullCommits) {
						List<Commit> commits = gitLookupService.getCommit(pc).get();
						
						if (commits != null && !commits.isEmpty()) {
							for (Commit c : commits) {
								commitsOfPR.add(c);
							}
						}
					}
				}
				
				if (!commitsOfPR.isEmpty()) {
					for (Commit c : commitsOfPR) {
						List<Commit.CommitFile> commitedFiles = c.getFiles();
						//TODO scan files
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}

}
