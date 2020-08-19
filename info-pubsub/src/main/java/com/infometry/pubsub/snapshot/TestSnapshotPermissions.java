package com.infometry.pubsub.snapshot;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.iam.v1.TestIamPermissionsResponse;
import com.google.pubsub.v1.ProjectSnapshotName;
import com.infometry.pubsub.PubSubConnection;

public class TestSnapshotPermissions {

	public static void main(String[] args)
	{
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		String snapshotId = "abcd";
		try (SubscriptionAdminClient snapshotAdminClient = SubscriptionAdminClient.create(SubscriptionAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())) 
		{
		      List<String> permissions = new LinkedList<>();
		      permissions.add("pubsub.snapshots.get");
		      permissions.add("pubsub.snapshots.delete");
		      permissions.add("pubsub.snapshots.getIamPolicy");
		      permissions.add("pubsub.snapshots.update");
		      ProjectSnapshotName snapshotName = ProjectSnapshotName.of(projectId, snapshotId);
		      TestIamPermissionsResponse testedPermissions =
		    		  snapshotAdminClient.testIamPermissions(snapshotName.toString(), permissions);
		      System.out.println(testedPermissions);
		    } catch (IOException e) {
				e.printStackTrace();
			}

	}

}
