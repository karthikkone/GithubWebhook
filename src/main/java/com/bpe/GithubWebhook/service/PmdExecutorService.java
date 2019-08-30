package com.bpe.GithubWebhook.service;

import java.io.File;
import java.io.IOException;

public interface PmdExecutorService {
	public String executeOnDir(File rootDir) throws IOException;
}
