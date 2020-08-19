package com.infometry.pubsub.topic;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.pubsub.v1.ProjectTopicName;
import com.infometry.pubsub.PubSubConnection;
public class DeleteTopic {
	public static void main(String... args) throws Exception 
	{
		CredentialsProvider credentialsProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		String topicId = "Twin";
			
		ProjectTopicName topic = ProjectTopicName.of(projectId, topicId);
		try (TopicAdminClient topicAdminClient = TopicAdminClient.create(TopicAdminSettings.newBuilder().setCredentialsProvider(credentialsProvider).build()))
				{
			topicAdminClient.deleteTopic(topic);
			System.out.printf("Topic %s:%s deleted.\n", topic.getProject(), topic.getTopic());
		} catch (ApiException e) {
			// example : code = ALREADY_EXISTS(409) implies topic already exists
			System.out.print(e.getStatusCode().getCode());
			System.out.print(e.isRetryable());
		}
	}
}
