package com.infometry.pubsub.subscription;

import java.io.IOException;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.pubsub.v1.ProjectSnapshotName;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.SeekRequest;
import com.google.pubsub.v1.SeekResponse;
import com.infometry.pubsub.PubSubConnection;

public class SeekSubscription {
	public static void main(String[] args) throws IOException 
	{
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		
		String subscriptionId = "subscription";
		String snapshot = "snapshot";
		ProjectSubscriptionName subscription = ProjectSubscriptionName.of(projectId, subscriptionId);
		ProjectSnapshotName snap = ProjectSnapshotName.of(projectId, snapshot);
		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create(SubscriptionAdminSettings.newBuilder().setCredentialsProvider(credProvider).build()))
		{
			SeekRequest request = SeekRequest.newBuilder().setSubscription(subscription.toString()).setSnapshot(snap.toString()).build();
			System.out.println("Request:"+request);
			SeekResponse response = subscriptionAdminClient.seek(request);
			System.out.println("Response"+response);
		} 
		catch (ApiException e) 
		{
			
		}
	}
}
