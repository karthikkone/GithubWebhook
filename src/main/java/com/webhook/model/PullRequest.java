package com.webhook.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = true)
public class PullRequest {

	private String number; //pull request number
	private String state; //pull state opened, created, closed etc.
	private String title;
	private String body;
	private Links _links;
	private boolean draft;
	private String id;
	
	
	
	public PullRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Data
    @ToString(includeFieldNames = true)
	static class Links {
		Commits commits;
		
		public Links() {
			super();
		}
	}

	@Data
    @ToString(includeFieldNames = true)
	static class Commits {
		String href;
		
		public Commits() {
			super();
		}
		
	}


}