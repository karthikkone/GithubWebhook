package com.bpe.GithubWebhook;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bpe.GithubWebhook.service.PmdExecutorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PmdExecutorTest {
	@Autowired
	private PmdExecutorService pmd;
	
	@Value("${workspace.path:/tmp}")
	private String workspace;
	
	@Test
	public void shouldReturnStringOutput() throws Exception {
		System.out.println("roor dir = "+workspace);
		File rootDir = new File(workspace);
		String output = pmd.executeOnDir(rootDir);
		System.out.println("PMD OUTPUT = "+output);
		//assert output != null;
	}
}
