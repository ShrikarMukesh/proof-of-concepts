package com.infometry.pubsub.topicSubcription;

import java.io.IOException;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminClient.ListTopicSubscriptionsPagedResponse;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.pubsub.v1.ListTopicSubscriptionsRequest;
import com.google.pubsub.v1.ProjectTopicName;
import com.infometry.pubsub.PubSubConnection;

public class ListTopicSubscription {

	public static void main(String[] args) throws IOException {

		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		//String topicname="projects/inf-pubsub/topics/Ribbon";
		String topicId = "Ribbon";

		try (TopicAdminClient topicAdminClient = TopicAdminClient.create(TopicAdminSettings.newBuilder().setCredentialsProvider(credProvider).build()))
		{
			ProjectTopicName topicName=ProjectTopicName.of(projectId, topicId);

			ListTopicSubscriptionsRequest listTopicsRequest = ListTopicSubscriptionsRequest.newBuilder()
					.setTopic(topicName.toString()).build();
			ListTopicSubscriptionsPagedResponse response = topicAdminClient.listTopicSubscriptions(listTopicsRequest);
			Iterable<String> subscriptionsList = response.iterateAll();
			for (String subscription : subscriptionsList) {
				System.out.println(subscription.toString());
			} 
		} 
		catch (ApiException e) {
			// example : code = ALREADY_EXISTS(409) implies topic already exists
			System.out.print(e.getStatusCode().getCode());
			System.out.print(e.isRetryable());
		}
	}
}
