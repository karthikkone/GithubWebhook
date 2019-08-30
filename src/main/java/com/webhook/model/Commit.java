package com.webhook.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(includeFieldNames=true)
@Builder
public class Commit {
    private String sha;
    @JsonProperty("commit")
    private CommitInfo commitInfo;
    private String url;
    private List<CommitFile> files;
    private List<CommitParent> parents;

    @Data
    @Builder
    public static class CommitInfo {
        private User author;
        private User commiter;
        private String message;
        @JsonProperty("url")
        private String gitUrl;
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
        private boolean verified;
        private String reason;
        private String signature;
    }

    @Data
    @ToString(includeFieldNames = true)
    @Builder
    public static class CommitFile {
        private String sha;
        private String filename;
        private String status;
        private int additions;
        private int deletions;
        private int changes;
        private String blob_url;
        private String raw_url;
        private String contents_url;
        private String patch;
    }

    @Data
    public static class CommitParent {
        private String sha;
        private String url;
    }
}
