package com.bpe.GithubWebhook;

import com.bpe.GithubWebhook.service.GitLookUpService;
import com.bpe.GithubWebhook.service.ScanService;
import com.webhook.model.PullCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.webhook.model.PullRequestPayload;

@RestController
public class WebhookServer {

    @Autowired
    private GitLookUpService gitLookUpService;
    @Autowired
    private ScanService scanService;
	
	@RequestMapping(value="/payload", method = RequestMethod.POST)
	public void getPayload(@RequestBody PullRequestPayload body) {
		System.out.println(body);
		/*CompletableFuture<List<PullCommit>> future = gitLookUpService.getCommits(body.getPull_request());

        try {
            List<PullCommit> commitList = future.get();
            System.out.println(commitList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
		scanService.initiate(body.getPull_request());
    }
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String home() {
		return "Github Wrapper Demo!";
	}
	
}
