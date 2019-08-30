package com.bpe.GithubWebhook;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.bpe.GithubWebhook.service.GitLookUpService;
import com.bpe.GithubWebhook.service.ScanService;
import com.bpe.GithubWebhook.service.ScanServiceImpl;
import com.webhook.model.Commit;
import com.webhook.model.PullCommit;
import com.webhook.model.PullRequest;

@RunWith(MockitoJUnitRunner.class)
public class ScanServiceTest {
	
	@Mock
	GitLookUpService gitLookUpService;
	
	@InjectMocks
	ScanService scanService = new ScanServiceImpl();
	
	@Test
	public void testInitiateScan() {
		PullRequest pr = new PullRequest();
		pr.setNumber("1");
		pr.setState("open");
		
		List<PullCommit> pcommits = new ArrayList<PullCommit>();
		PullCommit.User author = PullCommit.User.builder().email("sample_author@git.com").name("Arthur Sample").build();
		
		PullCommit.CommitInfo commitInfo = PullCommit.CommitInfo.builder()
				.author(author)
				.committer(author)
				.url("commitsApiUrl").build();
		
		PullCommit p = PullCommit.builder().commit(commitInfo).url("commitsApiUrl").sha("SHA-0").build();
		pcommits.add(p);
		
		Commit.CommitFile f0 = Commit.CommitFile.builder()
				.additions(2)
				.deletions(1)
				.changes(3)
				.filename("Sample.cls")
				.sha("FILE_SHA-0").build();
		
		Commit.CommitFile f1 = Commit.CommitFile.builder()
				.additions(5)
				.deletions(3)
				.changes(8)
				.filename("Sample.page")
				.sha("FILE_SHA-1").build();
		
		Commit.CommitFile f2 = Commit.CommitFile.builder()
				.additions(5)
				.deletions(3)
				.changes(8)
				.filename("Sample.apxt")
				.sha("FILE_SHA-2").build();
		List<Commit.CommitFile> filesInCommit0 = new ArrayList<Commit.CommitFile>();
		filesInCommit0.add(f0);
		filesInCommit0.add(f1);
		
		List<Commit.CommitFile> filesInCommit1 = new ArrayList<Commit.CommitFile>();
		filesInCommit1.add(f2);
		
		Commit c0 = Commit.builder().sha("SHA-0").files(filesInCommit0).build();
		Commit c1 = Commit.builder().sha("SHA-1").files(filesInCommit1).build();
		
		List<Commit> commitsOfPR = new ArrayList<Commit>();
		commitsOfPR.add(c0);
		commitsOfPR.add(c1);
		
		
		Mockito.when(gitLookUpService.getPullCommits(Mockito.any(PullRequest.class))).thenReturn(CompletableFuture.completedFuture(pcommits));
		Mockito.when(gitLookUpService.getCommit(Mockito.any(PullCommit.class))).thenReturn(CompletableFuture.completedFuture(commitsOfPR));
		
		scanService.initiate(pr);
	}
}
