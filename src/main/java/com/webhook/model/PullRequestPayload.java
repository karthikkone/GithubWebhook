package com.webhook.model;

public class PullRequestPayload {
	
	private String action; //pull event type opened, created etc;
	private Integer number;
	private PullRequest pull_request; //pull request associated with event
	
	public PullRequestPayload() {
		super();
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public PullRequest getPull_request() {
		return pull_request;
	}

	public void setPull_request(PullRequest pull_request) {
		this.pull_request = pull_request;
	}

	@Override
	public String toString() {
		return "PullRequestPayload [action=" + action + ", number=" + number + ", pull_request=" + pull_request + "]";
	}

	
}
