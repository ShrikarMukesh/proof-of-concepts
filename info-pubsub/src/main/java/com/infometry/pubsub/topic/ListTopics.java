package com.infometry.pubsub.topic;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminClient.ListTopicsPagedResponse;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.pubsub.v1.ListTopicsRequest;

import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Topic;
import com.infometry.pubsub.PubSubConnection;
public class ListTopics {
	static int i = 0;
	public static void main(String... args) throws Exception {

		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
		try (TopicAdminClient topicAdminClient = TopicAdminClient.create(TopicAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())){
			
			ListTopicsRequest listTopicsRequest = ListTopicsRequest.newBuilder()
					.setProject(ProjectName.format(projectId)).build();

			ListTopicsPagedResponse response = topicAdminClient.listTopics(listTopicsRequest);

			/*Iterable<Topic> itr = response.iterateAll();
			itr.forEach(topic->System.out.println(topic));*/

			//System.err.println(listTopicsRequest.getPageSize());
			Iterable<Topic> topics = response.iterateAll();

			/*Iterator<Topic> itr = topics.iterator();
			while(itr.hasNext()) {
				System.out.println(itr.next());
			}*/
			
			for (Topic topic : topics) {
				//System.out.println(topic);				
				String id = topic.getName();
				System.out.println(id);
				i++;
			}
			//System.err.println(i);
		} catch (ApiException e) {
			System.err.println(e.getMessage());
		}
	}
}