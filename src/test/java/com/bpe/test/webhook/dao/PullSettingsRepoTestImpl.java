package com.bpe.test.webhook.dao;

import org.springframework.stereotype.Service;

import com.bpe.webhook.dao.PullSettingsRepository;
import com.webhook.model.PullSettings;

@Service
public class PullSettingsRepoTestImpl implements PullSettingsRepository {

	@Override
	public PullSettings PullSettingsByUserId(String userId) {
		// TODO Auto-generated method stub
		if (userId == null) {
			return null;
		}
		
		PullSettings pullconf = new PullSettings();
		pullconf.setRepository("https://repo.git");
		pullconf.setBranch("master");
		pullconf.setUserId(userId);
		return pullconf;
	}

}
