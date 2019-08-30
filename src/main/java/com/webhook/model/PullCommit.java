package com.webhook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class PullCommit {
	private String sha;
	private String node_id;
	private CommitInfo commit;
	private String url; //commit url
	private List<CommitParents> parents;

	@Data
	@Builder
	public static class CommitInfo {
		private User author;
		private User committer;
		private String message;
		private String url; //commit url
		private Verification verification;
	}

	@Data
	@Builder
	public static class User {
		private String name;
		private String email;
		private String date;
	}

	@Data
	@Builder
	public static class Verification {
		private boolean verfied;
		private String reason;
		private String signature;
		private String payload;
	}

	@Data
	@Builder
	public static class CommitParents {
		private String sha;
		private String url;
		private String html_url;
	}

}
