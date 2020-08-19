package com.infometry.pubsub.snapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.InvalidArgumentException;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.protobuf.FieldMask;
import com.google.pubsub.v1.CreateSnapshotRequest;
import com.google.pubsub.v1.ProjectSnapshotName;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.Snapshot;
import com.google.pubsub.v1.UpdateSnapshotRequest;
import com.infometry.pubsub.PubSubConnection;

public class UpdateSnapshot {
	
	public static void main(String[] args) {
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		String snapshotId = "abcd";
		String subscriptionId = "abcd";
		Map<String, String> m = new HashMap<String, String>();
		m.put("name", "ashish");
		m.put("nick", "rinku");
		List<String> l = new ArrayList<String>();
		l.add("labels");
		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create(SubscriptionAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())) {
			ProjectSnapshotName name = ProjectSnapshotName.of(projectId, snapshotId);
//			ProjectSubscriptionName subscription = ProjectSubscriptionName.of(projectId, subscriptionId);
			
			Snapshot s = Snapshot.newBuilder().setName(name.toString()).putAllLabels(m).build();
			
			FieldMask updateMask = FieldMask.newBuilder().addAllPaths(l).build();
			
			UpdateSnapshotRequest request = UpdateSnapshotRequest.newBuilder().setSnapshot(s).setUpdateMask(updateMask).build();
			Snapshot s1=subscriptionAdminClient.updateSnapshot(request);
			System.out.println(s1);
		} catch (InvalidArgumentException | IOException e) {
		}
	}

}
