package com.infometry.pubsub.snapshot;

import java.io.IOException;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.iam.v1.Policy;
import com.google.pubsub.v1.ProjectSnapshotName;
import com.infometry.pubsub.PubSubConnection;

public class GetSnapshotPolicy {

	public static void main(String[] args) throws IOException 
	{
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
	    String snapshotId = "Info";
	    
	    ProjectSnapshotName snapshot = ProjectSnapshotName.of(projectId, snapshotId);
	    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create(SubscriptionAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())) {
	     Policy policy= subscriptionAdminClient.getIamPolicy(snapshot.toString());
	     System.out.printf("Snapshot %s:%s \n", snapshot.getProject(), policy);
	   if(policy==null)
	     {
	    	 System.out.printf("Snapshot %s:%s \n", snapshot.getProject(), policy); 
	     } 
	    } catch (ApiException e) {
	      // example : code = ALREADY_EXISTS(409) implies topic already exists
	      System.out.print(e.getStatusCode().getCode());
	      System.out.print(e.isRetryable());
	    }

	}

}
