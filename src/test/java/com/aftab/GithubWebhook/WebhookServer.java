package com.aftab.GithubWebhook;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookServer {
	
	@RequestMapping(value="/payload", method = RequestMethod.POST)
	public void getPayload(@RequestBody String body) {
		System.out.println(body);
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String home() {
		return "Github Wrapper Demo!";
	}
	
}
