package com.webhook.model;

public class PullRequestPayload {
	
	private String action; //pull event type opened, created etc;
	private Integer number;
	private PullRequest pull_request; //pull request associated with event
	
	@Override
	public String toString() {
		return "PullRequestPayload [action=" + action + ", number=" + number + ", pull_request=" + pull_request + "]";
	}

	
}
