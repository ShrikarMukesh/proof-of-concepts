package com.infometry.pubsub.subscription;

import java.io.IOException;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.iam.v1.Policy;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.infometry.pubsub.PubSubConnection;

public class GetSubscriptionPolicy {

	public static void main(String[] args) throws IOException
	{
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
	    String subscriptionId = "Neycer";

	    ProjectSubscriptionName subscription = ProjectSubscriptionName.of(projectId, subscriptionId);
	    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create(SubscriptionAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())) {
	     Policy policy= subscriptionAdminClient.getIamPolicy(subscription.toString());
	     
	     //System.out.printf("Subscription %s:%s \n", subscription.getProject(), policy); 
	   if(policy==null)
	     {
	    	 System.out.printf("Subscription %s:%s \n", subscription.getProject(), policy); 
	     } 
	    } 
	    catch (ApiException e)
	    {
	      System.out.print(e.getStatusCode().getCode());
	      System.out.print(e.isRetryable());
	    }
	}
}
