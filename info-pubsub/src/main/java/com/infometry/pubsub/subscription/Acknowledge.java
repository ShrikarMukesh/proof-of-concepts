package com.infometry.pubsub.subscription;

import java.io.IOException;

import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.pubsub.v1.stub.GrpcSubscriberStub;
import com.google.cloud.pubsub.v1.stub.SubscriberStub;
import com.google.cloud.pubsub.v1.stub.SubscriberStubSettings;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PullRequest;
import com.google.pubsub.v1.PullResponse;
import com.infometry.pubsub.PubSubConnection;

public class Acknowledge {

	public static void main(String[] args) {
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		String subid = "subscription";
		try (SubscriberStub subscriber = GrpcSubscriberStub.create(SubscriberStubSettings.newBuilder().setCredentialsProvider(credProvider).build())) {

			String subscriptionName = ProjectSubscriptionName.format(projectId, subid);
			PullRequest pullRequest =
					PullRequest.newBuilder()
					.setMaxMessages(5)
					.setReturnImmediately(false) // return immediately if messages are not available
					.setSubscription(subscriptionName)
					.build();

			// use pullCallable().futureCall to asynchronously perform this operation
			PullResponse pullResponse = subscriber.pullCallable().call(pullRequest);
			for(int i=0;i<pullResponse.getReceivedMessagesCount();i++){
				System.out.println(pullResponse.getReceivedMessages(i).getMessage().getData().toStringUtf8());
				System.err.println(pullResponse.getReceivedMessages(i).getAckId());
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}