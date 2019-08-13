package com.bpe.webhook.dao;

import com.webhook.model.PullSettings;

public interface PullSettingsRepository {
	public PullSettings PullSettingsByUserId(String userId);
}
