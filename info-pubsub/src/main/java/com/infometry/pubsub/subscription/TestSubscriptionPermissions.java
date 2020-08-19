package com.infometry.pubsub.subscription;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.iam.v1.TestIamPermissionsResponse;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.infometry.pubsub.PubSubConnection;

public class TestSubscriptionPermissions {

	public static void main(String[] args) {
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		String subscriptionId = "asgard";
		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create(SubscriptionAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())) {
		      List<String> permissions = new LinkedList<>();
		      permissions.add("pubsub.subscriptions.get");
		      permissions.add("pubsub.subscriptions.delete");
		      permissions.add("pubsub.subscriptions.getIamPolicy");
		      permissions.add("pubsub.subscriptions.update");
		      ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);
		      TestIamPermissionsResponse testedPermissions =
		    		  subscriptionAdminClient.testIamPermissions(subscriptionName.toString(), permissions);
		      System.out.println(testedPermissions);
		    } catch (IOException e) {
				e.printStackTrace();
			}
	}
}
