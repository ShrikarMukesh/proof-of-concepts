package com.infometry.pubsub.topic;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.Topic;
import com.infometry.pubsub.PubSubConnection;

public class GetTopic {
	public static void main(String... args) throws Exception {
		
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		String topicId = "Spring";
		ProjectTopicName topic = ProjectTopicName.of(projectId, topicId);
		try (TopicAdminClient topicAdminClient = TopicAdminClient.create(TopicAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())) {
			Topic topicname=topicAdminClient.getTopic(topic);
			System.out.printf("Topic %s:%s \n", topic.getProject(), topicname);
		} 
		catch (ApiException e) 
		{
			System.out.print(e.getStatusCode().getCode());
			System.out.print(e.isRetryable());
		}
	}
}
