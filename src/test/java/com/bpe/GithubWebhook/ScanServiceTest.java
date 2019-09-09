package com.bpe.GithubWebhook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;

import com.bpe.GithubWebhook.service.GitLookUpService;
import com.bpe.GithubWebhook.service.PmdExecutorService;
import com.bpe.GithubWebhook.service.PmdExecutorServiceImpl;
import com.bpe.GithubWebhook.service.ScanService;
import com.bpe.GithubWebhook.service.ScanServiceImpl;
import com.webhook.model.Commit;
import com.webhook.model.PullCommit;
import com.webhook.model.PullRequest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ScanServiceTest {
	
	@Mock
	GitLookUpService gitLookUpService;
	
	@Mock
	PmdExecutorService pmd = new PmdExecutorServiceImpl();
	
	
	@InjectMocks
	ScanService scanService = new ScanServiceImpl();
	
	@Before
	public void setUp() {
		ReflectionTestUtils.setField(scanService, "pmdPath", "./pmd.bat");
		ReflectionTestUtils.setField(scanService, "rulesPath", "./rulesets");
		ReflectionTestUtils.setField(scanService, "pmdOutputFormat", "text");
		ReflectionTestUtils.setField(scanService, "workspace", "./workspace");
	}
	
	@Test
	public void testInitiateScan() throws IOException {
		Resource testApexClass0 = new ClassPathResource("Hello.cls");
		Resource testApexClass1 = new ClassPathResource("samples/TestHello.cls");
		Resource testApexPage0 = new ClassPathResource("Hello.page");
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
				.filename("Hello.cls")
				.sha("FILE_SHA-0")
				.raw_url(testApexClass0.getURI().toString())
				.build();
		
		Commit.CommitFile f1 = Commit.CommitFile.builder()
				.additions(5)
				.deletions(3)
				.changes(8)
				.filename("Hello.page")
				.sha("FILE_SHA-1")
				.raw_url(testApexPage0.getURI().toString())
				.build();
		
		Commit.CommitFile f2 = Commit.CommitFile.builder()
				.additions(5)
				.deletions(3)
				.changes(8)
				.filename("TestHello.cls")
				.sha("FILE_SHA-2")
				.raw_url(testApexClass1.getURI().toString())
				.build();
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
		Mockito.when(pmd.executeOnDir(Mockito.any())).thenReturn("sample error on line:1");
		assert scanService != null;
		
		scanService.initiate(pr);
	}
}
