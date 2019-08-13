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
	public Links get_links() {
		return _links;
	}

	public void set_links(Links _links) {
		this._links = _links;
	}


	private boolean draft;
	private String id;
	
	
	
	public PullRequest() {
		super();
		// TODO Auto-generated constructor stub
	}


	class Links {
		Commits commits;
		
		public Links() {
			super();
		}

		public Commits getCommits() {
			return commits;
		}

		public void setCommits(Commits commits) {
			this.commits = commits;
		}
	}
	
	class Commits {
		String href;
		
		public Commits() {
			super();
		}
		public String getHref() {
			return href;
		}

		public void setHref(String href) {
			this.href = href;
		}
		
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