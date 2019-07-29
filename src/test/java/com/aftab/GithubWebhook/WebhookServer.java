package com.aftab.GithubWebhook;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookServer {
	
	@RequestMapping(path="/payload")
	public void getPayload(@RequestBody String body) {
		System.out.println(body);
	}
}
