package com.bpe.GithubWebhook.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.webhook.model.Commit;
import com.webhook.model.PullCommit;
import com.webhook.model.PullRequest;


@Service
public class ScanServiceImpl implements ScanService {
	
	@Value("${pmd.path}")
	private String pmdPath;
	
	@Value("{$workspace.path:/tmp}")
	private String workspace;
	
	@Value("{$pmd.rulespath}")
	private String rulesPath;
	
	@Value("{$pmd.output:text}")
	private String pmdOutputFormat;
	
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
				
				System.out.println("PULLCOMMITS : "+pullCommits);
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
				System.out.println("Commits of Pull request: "+commitsOfPR);
				if (!commitsOfPR.isEmpty()) {
					//create a directory for current PullRequest
					new File(""+workspace+"/"+pr.getNumber());
					
					for (Commit c : commitsOfPR) {
						List<Commit.CommitFile> commitedFiles = c.getFiles();
						//create a directory for commit
						File filesOfCommit = new File(""+workspace+"/"+pr.getNumber()+"/"+c.getSha());
						
						//fetch and save changed files to commits directory
						for (Commit.CommitFile f: commitedFiles) {
							FileUtils.copyURLToFile(new URL(f.getRaw_url()), filesOfCommit);
						}
					}
					
					//execute pmd
					
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}

}
