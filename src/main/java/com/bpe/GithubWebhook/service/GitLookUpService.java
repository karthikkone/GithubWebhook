package com.bpe.GithubWebhook.service;

import com.webhook.model.PullCommit;
import com.webhook.model.PullRequest;
import com.webhook.model.PullRequestPayload;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GitLookUpService {
    public CompletableFuture<List<PullCommit>> getCommits(PullRequest pullRequest);
}
