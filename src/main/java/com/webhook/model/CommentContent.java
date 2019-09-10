package com.webhook.model;

import lombok.Data;
import lombok.ToString;

/*
 * Comment on pull request
 */
@Data
@ToString(includeFieldNames = true)
public class CommentContent {
	private String body;
}
