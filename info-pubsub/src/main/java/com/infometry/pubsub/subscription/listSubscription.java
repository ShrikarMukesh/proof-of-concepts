package com.infometry.pubsub.subscription;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient.ListSubscriptionsPagedResponse;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.pubsub.v1.ListSubscriptionsRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Subscription;
import com.infometry.pubsub.PubSubConnection;

public class listSubscription {

	public static void main(String[] args) throws Exception {
		//String projectId = ServiceOptions.getDefaultProjectId();

		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		//ProjectName project = ProjectName.of(projectId);

		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create(SubscriptionAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())) {

			ListSubscriptionsRequest listSubscriptionRequest = ListSubscriptionsRequest.newBuilder().setPageSize(3)
					.setProject(ProjectName.format(projectId)).build();
			ListSubscriptionsPagedResponse response = subscriptionAdminClient.listSubscriptions(listSubscriptionRequest);
			Iterable<Subscription> subscriptions = response.iterateAll();
			for (Subscription subscription : subscriptions) {
				System.out.println(subscription.getName());
			}
		} catch (ApiException e) {

			System.out.print(e.getStatusCode().getCode());
			System.out.print(e.isRetryable());
		}
	}

}
