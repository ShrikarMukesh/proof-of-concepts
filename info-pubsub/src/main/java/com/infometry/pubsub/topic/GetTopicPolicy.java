package com.infometry.pubsub.topic;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.iam.v1.Policy;
import com.google.pubsub.v1.ProjectTopicName;
import com.infometry.pubsub.PubSubConnection;

public class GetTopicPolicy {

	public static void main(String[] args) throws Exception
	{
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
	    String topicId = "Spring";
	 
	    ProjectTopicName topic = ProjectTopicName.of(projectId, topicId);
	    try (TopicAdminClient topicAdminClient = TopicAdminClient.create(TopicAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())) {
	     Policy policy= topicAdminClient.getIamPolicy(topic.toString());
	     System.out.printf("Topic %s:%s \n", topic.getProject(), policy); 
	     if(policy==null)
	     {
	    	 System.out.printf("Topic %s:%s \n", topic.getProject(), policy); 
	     } 
	    } catch (ApiException e) {
	      // example : code = ALREADY_EXISTS(409) implies topic already exists
	      System.out.print(e.getStatusCode().getCode());
	      System.out.print(e.isRetryable());
	    }

	}

}
