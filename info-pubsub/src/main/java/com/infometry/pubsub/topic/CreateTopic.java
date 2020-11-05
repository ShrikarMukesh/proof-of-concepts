package com.infometry.pubsub.topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.pubsub.v1.MessageStoragePolicy;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.Topic;
import com.infometry.pubsub.PubSubConnection;

public class CreateTopic {

	public static void main(String... args) throws Exception{

		String topicId = "abcd";
		Map<String, String> m = new HashMap<String, String>();
		m.put("name", "value");
		List<String> l = new ArrayList<String>();
		l.add("asia-east1");
		l.add("asia-south1");
		MessageStoragePolicy msg = MessageStoragePolicy.newBuilder().addAllAllowedPersistenceRegions(l).build();

		String projectId1=PubSubConnection.getProjectId();
		CredentialsProvider conn=PubSubConnection.getCredentials();
		ProjectTopicName topicName = ProjectTopicName.of(projectId1, topicId);
		try (TopicAdminClient topicAdminClient = TopicAdminClient.create(TopicAdminSettings.newBuilder().setCredentialsProvider(conn).build())) {
			Topic topicC;

			topicC=Topic.newBuilder().setName(topicName.toString()).putAllLabels(m).setMessageStoragePolicy(msg)
					.build();

			//			Topic response = topicAdminClient.createTopic(topicName);
			Topic response = topicAdminClient.createTopic(topicC);
			System.out.println(response);
		} 
		catch (ApiException e) {
			// example : code = ALREADY_EXISTS(409) implies topic already exists
			System.out.print(e.getStatusCode().getCode());
			System.out.print(e.isRetryable());
		}
	}
}