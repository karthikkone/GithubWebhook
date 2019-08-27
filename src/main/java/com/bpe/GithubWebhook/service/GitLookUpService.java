package com.bpe.GithubWebhook.service;

import com.webhook.model.Commit;
import com.webhook.model.PullCommit;
import com.webhook.model.PullRequest;
import com.webhook.model.PullRequestPayload;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GitLookUpService {
    public CompletableFuture<List<PullCommit>> getPullCommits(PullRequest pullRequest);
    public CompletableFuture<List<Commit>> getCommit(PullCommit pullCommit);
}
