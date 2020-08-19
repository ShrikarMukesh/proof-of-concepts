package com.infometry.pubsub.subscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.pubsub.v1.ModifyAckDeadlineRequest;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.infometry.pubsub.PubSubConnection;

public class ModifyAckDeadline {

	public static void main(String[] args) throws IOException 
	{
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		
	    String subscriptionId = "logic";
	    ProjectSubscriptionName subscription = ProjectSubscriptionName.of(projectId, subscriptionId);
	    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create(SubscriptionAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())) {  
	    	  List<String> ackId=new ArrayList<>();
	    	  int ackDeadLineSeconds=20;
	 
	    	 ModifyAckDeadlineRequest request =
	    			   ModifyAckDeadlineRequest.newBuilder()
	    			     .setSubscription(subscription.toString())
	    			     .addAllAckIds(ackId)
	    			     .setAckDeadlineSeconds(ackDeadLineSeconds)
	    			     .build();
	    	System.out.println(request);
	    	
	    	  }
	    catch (ApiException e) {
		      // example : code = ALREADY_EXISTS(409) implies topic already exists
		      System.out.print(e.getStatusCode().getCode());
		      System.out.print(e.isRetryable());
		    }
	} 
}
	    	  