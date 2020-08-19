package com.infometry.pubsub.subscription;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.Subscription;
import com.infometry.pubsub.PubSubConnection;
public class GetSubscription {
	public static void main(String... args) throws Exception {
		CredentialsProvider credentialsProvider = PubSubConnection.getCredentials();
		String projectId = PubSubConnection.getProjectId();
		String subscriptionId = "withAll";
		ProjectSubscriptionName subscription = ProjectSubscriptionName.of(projectId, subscriptionId);
		try {
			SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient
					.create(SubscriptionAdminSettings.newBuilder().setCredentialsProvider(credentialsProvider).build());
			Subscription r = subscriptionAdminClient.getSubscription(subscription);
			System.out.println(r);
			System.out.println(r.getPushConfig().getAttributesCount());
		} 
		catch (ApiException e) {
		}
	}
}