package com.infometry.pubsub.snapshot;

import java.io.IOException;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient.ListSnapshotsPagedResponse;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.pubsub.v1.ListSnapshotsRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Snapshot;
import com.infometry.pubsub.PubSubConnection;

public class ListSnapshot {

	public static void main(String[] args) throws IOException 
	{
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		ProjectName project = ProjectName.of(projectId);
		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create(SubscriptionAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())) {
			@SuppressWarnings("unused")
			ListSnapshotsPagedResponse listSnapshots = subscriptionAdminClient.listSnapshots(project);
			ListSnapshotsRequest listsnapshotRequest = ListSnapshotsRequest.newBuilder()
					.setProject(ProjectName.format(projectId)).build();
			ListSnapshotsPagedResponse response = subscriptionAdminClient.listSnapshots(listsnapshotRequest);
			Iterable<Snapshot> snapshots = response.iterateAll();
			for (Snapshot snapshot : snapshots) {
				System.out.println(snapshot);
			}
		} catch (ApiException e) {
			System.out.print(e.getStatusCode().getCode());
			System.out.print(e.isRetryable());
		}

	}

}
