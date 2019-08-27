package com.bpe.GithubWebhook.service;

import com.webhook.model.PullRequest;

public interface ScanService {
	public void initiate(PullRequest pr);
}
