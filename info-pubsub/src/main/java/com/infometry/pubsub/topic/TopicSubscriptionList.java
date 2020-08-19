package com.infometry.pubsub.topic;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminClient.ListTopicSubscriptionsPagedResponse;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.iam.v1.TestIamPermissionsResponse;
import com.google.pubsub.v1.ListTopicSubscriptionsRequest;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.Subscription;
import com.infometry.pubsub.PubSubConnection;

public class TopicSubscriptionList {

	public static void main(String[] args) {
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		String topic = "tony";
		String nextToken = "";
		try (TopicAdminClient topicAdminClient = TopicAdminClient.create(TopicAdminSettings.newBuilder().setCredentialsProvider(credProvider).build()))
		{
			ProjectTopicName topicName=ProjectTopicName.of(projectId, topic);
			ListTopicSubscriptionsRequest listTopicsRequest = ListTopicSubscriptionsRequest.newBuilder()
					.setTopic(topicName.toString()).setPageToken(nextToken).build();
			ListTopicSubscriptionsPagedResponse response = topicAdminClient.listTopicSubscriptions(listTopicsRequest);
		
			Iterable<String> subs = response.iterateAll();

			for(String sub : subs) {
				System.out.println(sub);
				
			}
		    } catch (IOException e) {
				e.printStackTrace();
			}
	}
}