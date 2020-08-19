package com.infometry.pubsub.snapshot;

import java.io.IOException;

import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.Identity;
import com.google.cloud.Role;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.iam.v1.Binding;
import com.google.iam.v1.Policy;
import com.google.pubsub.v1.ProjectSnapshotName;
import com.infometry.pubsub.PubSubConnection;

public class SetSnapshotPolicy {

	public static void main(String[] args) 
	{
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		String snapshotId = "Info";
		
		try (SubscriptionAdminClient snapshotAdminClient = SubscriptionAdminClient.create(SubscriptionAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())) {
		      ProjectSnapshotName snapshotName =
		    		  ProjectSnapshotName.of(projectId, snapshotId);
		      Policy policy = snapshotAdminClient.getIamPolicy(snapshotName.toString());
		      // Create a role => members binding
		      Binding binding =
		          Binding.newBuilder()
		              .setRole(Role.viewer().toString())
		              .addMembers(Identity.allAuthenticatedUsers().toString())
		              .build();
		      // Update policy
		      Policy updatedPolicy = policy.toBuilder().addBindings(binding).build();
		      updatedPolicy =
		    		  snapshotAdminClient.setIamPolicy(snapshotName.toString(), updatedPolicy);
		      System.out.println(updatedPolicy);
		    } catch (IOException e) {
				e.printStackTrace();
			}

	}

}
