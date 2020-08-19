package com.infometry.pubsub.subscription;

import java.io.IOException;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PushConfig;
import com.infometry.pubsub.PubSubConnection;

public class ModifyRunConfigSubscription {

	public static void main(String[] args) throws IOException {
			
		       //String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
	           String subscriptionId = "Neycer";
	           String endpoint="https://example1/push";
			    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create(SubscriptionAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())) 
			    {
			      ProjectSubscriptionName subscriptionName =
			          ProjectSubscriptionName.of(projectId, subscriptionId);
			      PushConfig pushConfig = PushConfig.newBuilder().setPushEndpoint(endpoint).build();
			      subscriptionAdminClient.modifyPushConfig(subscriptionName, pushConfig);
			      System.out.println(subscriptionName+" modified with "+endpoint+" endpoint");
			    }
			    catch (ApiException e) 
				{
				      System.out.print(e.getStatusCode().getCode());
				      System.out.print(e.isRetryable());
				    }
			  }
	}
