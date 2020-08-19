package com.pubsub.test;

import java.io.IOException;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.ProjectTopicName;

public class Test {
	public static void main(String[] args) throws IOException {

		ProjectTopicName topic = ProjectTopicName.of("test-project", "test-topic");
		try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
			topicAdminClient.createTopic(topic);
		}		
	}
}
