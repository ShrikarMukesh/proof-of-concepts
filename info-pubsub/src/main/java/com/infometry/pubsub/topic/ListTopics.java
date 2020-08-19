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
	public static void main(String... args) throws Exception {
		
		//String projectId = ServiceOptions.getDefaultProjectId();
		CredentialsProvider credProvider=PubSubConnection.getCredentials();
		String projectId=PubSubConnection.getProjectId();
	
		//ProjectName project = ProjectName.of(projectId);
		try (TopicAdminClient topicAdminClient = TopicAdminClient.create(TopicAdminSettings.newBuilder().setCredentialsProvider(credProvider).build()))
		{
			int i = 0;
			 
			 
			ListTopicsRequest listTopicsRequest = ListTopicsRequest.newBuilder()
					.setProject(ProjectName.format(projectId)).setPageToken("DBsGUAUZcXlmdm9eFAILQFt9").setPageSize(1).build();
			
			ListTopicsPagedResponse response = topicAdminClient.listTopics(listTopicsRequest);
			System.err.println(listTopicsRequest.getPageSize());
			Iterable<Topic> topics = response.iterateAll();
			
			for (Topic topic : topics) {
				System.out.println(topic);
				i++;
			}
			System.err.println(i);
		} catch (ApiException e) {
			System.err.println(e.getMessage());
			/*System.out.print(e.getStatusCode().getCode());
			System.out.print(e.isRetryable());*/
		}
	}
}