package com.infometry.pubsub.subscription;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.infometry.pubsub.PubSubConnection;

public class DeleteSubscription {

	public static void main(String[] args) throws Exception 
	{
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		String subscriptionId = "cred";
		ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);
		try (SubscriptionAdminClient subscriberAdminClient = SubscriptionAdminClient.create(SubscriptionAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())) {
			subscriberAdminClient.deleteSubscription(subscriptionName);
			System.out.printf("Subscription %s:%s deleted.\n", subscriptionName.getProject(), subscriptionName.getSubscription());
	}
		catch (ApiException e) {
			// example : code = ALREADY_EXISTS(409) implies topic already exists
			System.out.print(e.getStatusCode().getCode());
			System.out.print(e.isRetryable());
		}
	}

}
