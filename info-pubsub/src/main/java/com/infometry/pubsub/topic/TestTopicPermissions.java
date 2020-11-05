package com.infometry.pubsub.topic;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.iam.v1.TestIamPermissionsResponse;
import com.google.pubsub.v1.ProjectTopicName;
import com.infometry.pubsub.PubSubConnection;
public class TestTopicPermissions {
	public static void main(String[] args) {
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		String topicId = "topic";
		try (TopicAdminClient topicAdminClient = TopicAdminClient.create(TopicAdminSettings.newBuilder().setCredentialsProvider(credProvider).build()))
		{
		      List<String> permissions = new LinkedList<>();
		      permissions.add("pubsub.topics.get");
//		      permissions.add("pubsub.topics.getIamPolicy");
//		      permissions.add("pubsub.topics.delete");
//		      permissions.add("pubsub.topics.update");
//		      permissions.add("pubsub.topics.publish");
//		      
		      //TestIamPermissionsRequest r = TestIamPermissionsRequest.newBuilder().p
		      ProjectTopicName topicName = ProjectTopicName.of(projectId, topicId);
		      TestIamPermissionsResponse testedPermissions =
		          topicAdminClient.testIamPermissions(topicName.toString(), permissions);
		      System.out.println(testedPermissions);
		    } catch (IOException e) {
				e.printStackTrace();
			}
	}
}