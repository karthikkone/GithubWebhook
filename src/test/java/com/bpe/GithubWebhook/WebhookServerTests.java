package com.bpe.GithubWebhook;

import static org.assertj.core.api.Assertions.assertThat;

import com.bpe.GithubWebhook.service.GitLookUpService;
import com.webhook.model.PullCommit;
import com.webhook.model.PullRequest;
import com.webhook.model.PullRequestPayload;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WebhookServerTests {
	
	@LocalServerPort
	private int port;


	@Autowired
	private TestRestTemplate restTemplate;


	@Autowired
	WebhookServer controller;

	
	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/", String.class)).contains("Github Wrapper Demo!");
	}

	@Test
	public void shouldParsePullRequestEvent() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(GitHubTestData.PULL_REQUEST_OPENED, headers);
        restTemplate.postForObject("http://localhost:"+port+"/payload",entity,String.class);

    }
}
