package com.infometry.pubsub.topic;

import java.io.IOException;
import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.Identity;
import com.google.cloud.Role;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.iam.v1.Binding;
import com.google.iam.v1.Policy;
import com.google.iam.v1.SetIamPolicyRequest;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.type.Expr;
import com.infometry.pubsub.PubSubConnection;
public class SetTopicPolicy {
	public static void main(String[] args) {
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		String topicId = "tony";
		int v = 2;
		String ex = "AShish";
		String d = "Kumar";
		String t = "Patel";
		String l = "Korba";
		try (TopicAdminClient topicAdminClient = TopicAdminClient.create(TopicAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())) {
		      ProjectTopicName topicName =
		    		  ProjectTopicName.of(projectId, topicId);
		      Policy policy = topicAdminClient.getIamPolicy(topicName.toString());
		      // Create a role => members binding
		      Expr e = Expr.newBuilder().setExpression(ex).setDescription(d).setTitle(t).setLocation(l).build();
		      Binding binding =
		          Binding.newBuilder()
		              .setRole(Role.viewer().toString())
		              .addMembers(Identity.allAuthenticatedUsers().toString()).setCondition(e)
		              .build();
		      // Update policy
		      
		      Policy updatedPolicy = Policy.newBuilder().addBindings(binding).setEtag(policy.getEtag()).setVersion(v)
		    		  .build();
		     
		     	System.out.println(updatedPolicy);	      
		      System.out.println(topicAdminClient.setIamPolicy(topicName.toString(), updatedPolicy));
		    } catch (Exception e) {
				e.printStackTrace();
			}
	}
}