package com.infometry.pubsub.subscription;

import java.io.IOException;

import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.Identity;
import com.google.cloud.Role;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.iam.v1.Binding;
import com.google.iam.v1.Policy;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.infometry.pubsub.PubSubConnection;

public class SetSubscriptionPolicy {

	public static void main(String[] args) 
	{
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		String subscriptionId = "Neycer";
		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create(SubscriptionAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())) {
		      ProjectSubscriptionName subscriptionName =
		    		  ProjectSubscriptionName.of(projectId, subscriptionId);
		      Policy policy = subscriptionAdminClient.getIamPolicy(subscriptionName.toString());
		      // Create a role => members binding
		      Binding binding =
		          Binding.newBuilder()
		              .setRole(Role.viewer().toString())
		              .addMembers(Identity.allAuthenticatedUsers().toString())
		              .build();
		      // Update policy
		      Policy updatedPolicy = policy.toBuilder().addBindings(binding).build();
		      updatedPolicy =
		    		  subscriptionAdminClient.setIamPolicy(subscriptionName.toString(), updatedPolicy);
		      System.out.println(updatedPolicy);
		    } catch (IOException e) {
				e.printStackTrace();
			}

	}

}
