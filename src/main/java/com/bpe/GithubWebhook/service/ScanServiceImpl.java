package com.bpe.GithubWebhook.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
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
	
	@Value("${workspace.path}")
	private String workspace;
	
	@Value("${pmd.rulespath}")
	private String rulesPath;
	
	@Value("${pmd.output:text}")
	private String pmdOutputFormat;
	
	@Autowired
	private GitLookUpService gitLookupService;
	
	@Autowired
	private PmdExecutorService pmd;
	
	@Override
	@Async
	public void initiate(PullRequest pr) {
		System.out.println("INFO: pmd PATH = "+pmdPath);
		System.out.println("INFO: Initiating scan on workspace : "+workspace);
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
					Path path = Paths.get(workspace, pr.getNumber());
					if (Files.notExists(path)) {
						Files.createDirectories(path);
					}
					
					for (Commit c : commitsOfPR) {
						List<Commit.CommitFile> commitedFiles = c.getFiles();
						//create a directory for commit
						Path commitPath = Paths.get(workspace, pr.getNumber(),c.getSha());
						if (Files.notExists(commitPath)) {
							Files.createDirectories(commitPath);
						}
						
						
						//fetch and save changed files to commits directory
						for (Commit.CommitFile f: commitedFiles) {
							File dest = commitPath.resolve(f.getFilename()).toFile();
							FileUtils.copyURLToFile(new URL(f.getRaw_url()), dest);
						}
					}
					
					//execute pmd
					String scanResults = pmd.executeOnDir(new File(workspace));
					
					//post results as pull request comment
					if (scanResults != null && !scanResults.isEmpty()) {
						gitLookupService.postComment(pr, scanResults);
					} else {
						gitLookupService.postComment(pr, "No issues detected");
					}
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
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
	}

}
