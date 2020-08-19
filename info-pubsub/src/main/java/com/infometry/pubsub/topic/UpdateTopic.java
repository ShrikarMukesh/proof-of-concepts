package com.infometry.pubsub.topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.common.collect.Iterables;
import com.google.protobuf.FieldMask;
import com.google.pubsub.v1.MessageStoragePolicy;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.UpdateTopicRequest;
import com.infometry.pubsub.PubSubConnection;

public class UpdateTopic {
	
	public static void main(String... args) throws Exception
	{
		//String projectId = ServiceOptions.getDefaultProjectId();
		String topicId = "name";
	    Map<String, String> m = new HashMap<String, String>();
	    m.put("name", "value");
	    List<String> l = new ArrayList<String>();
	    l.add("us-west4");
	    l.add("us-west1");
	    MessageStoragePolicy msg = MessageStoragePolicy.newBuilder().addAllAllowedPersistenceRegions(l).build();
	    List<String> abc = new ArrayList<String>();
	    abc.add("labels");
	    abc.add("message_storage_policy.allowed_persistence_regions");
	    
	    // Create a new topic
	   String projectId1=PubSubConnection.getProjectId();
	    CredentialsProvider conn=PubSubConnection.getCredentials();
	    ProjectTopicName topicName = ProjectTopicName.of(projectId1, topicId);
	    try (TopicAdminClient topicAdminClient = TopicAdminClient.create(TopicAdminSettings.newBuilder().setCredentialsProvider(conn).build())) {
			Topic topicC;
			
			topicC=Topic.newBuilder().setName(topicName.toString()).putAllLabels(m).setMessageStoragePolicy(msg)
					.build();
			
			FieldMask updateMask = FieldMask.newBuilder().addAllPaths(abc).build();
			//FieldMask updateMask = FieldMask.newBuilder().addPaths("labels").build();
			//FieldMask updateMask = FieldMask.newBuilder().add
			
			System.out.println(updateMask);
			System.out.println("====================================================");
			
			 UpdateTopicRequest request = UpdateTopicRequest.newBuilder()
				     .setTopic(topicC)
				     .setUpdateMask(updateMask).build();
			 
			 System.out.println(request);
			 System.out.println("====================================================");
			
			 Topic response = topicAdminClient.updateTopic(request);
			 
			 System.out.println(response);
			 System.out.println("====================================================");
	    } 
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}

}
