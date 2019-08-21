package com.webhook.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = true)
public class PullRequestPayload {
	
	private String action; //pull event type opened, created etc;
	private Integer number;
	private PullRequest pull_request; //pull request associated with event
	
	public PullRequestPayload() {
		super();
	}
}
