package com.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PullCommit {
	private String sha;
	private String node_id;
	private Commit commit;
	private String url; //commit url
	private List<CommitParents> parents;

	@Data
	static class Commit {
		private User author;
		private User committer;
		private String message;
		private String url; //commit url
		private Verification verification;
	}

	@Data
	static class User {
		private String name;
		private String email;
		private String date;
	}

	@Data
	static class Verification {
		private boolean verfied;
		private String reason;
		private String signature;
		private String payload;
	}

	@Data
	static class CommitParents {
		private String sha;
		private String url;
		private String html_url;
	}

}
