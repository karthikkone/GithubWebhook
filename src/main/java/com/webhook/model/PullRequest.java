package com.webhook.model;

public class PullRequest {
	
	@Override
	public String toString() {
		return "PullRequest [number=" + number + ", state=" + state + ", title=" + title + ", body=" + body
				+ ", _links=" + _links + ", draft=" + draft + ", id=" + id + "]";
	}


	private String number; //pull request number
	private String state; //pull state opened, created, closed etc.
	private String title;
	private String body;
	private Links _links;
	private boolean draft;
	private String id;
	
	class Links {
		Commits commits;
	}
	
	class Commits {
		String href;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isDraft() {
		return draft;
	}

	public void setDraft(boolean draft) {
		this.draft = draft;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getCommitsLink() {
		return this._links.commits.href;
	}
}