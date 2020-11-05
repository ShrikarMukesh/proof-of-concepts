package com.infometry.pubsub.subscription;

import java.util.HashMap;
import java.util.Map;

import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.protobuf.Duration;
import com.google.pubsub.v1.ExpirationPolicy;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.PushConfig.OidcToken;
import com.google.pubsub.v1.Subscription;
import com.infometry.pubsub.PubSubConnection;

/**
 * A snippet for Google Cloud Pub/Sub showing how to create a Pub/Sub pull
 * subscription and asynchronously pull messages from it.
 */
public class CreateSubscriptionAndConsumeMessages {

	//private static String projectId = "inf-pubsub";
	static CredentialsProvider credProvider=PubSubConnection.getCredentials();
	static String projectId=PubSubConnection.getProjectId();
	private static String topicId = "tony";
	private static String subscriptionId = "abcd932";
	static int sec = 30; //10>sec<600
	private static long duration1=604; //10 >=duration<= 10080
	private static long tt=5000;
	private static boolean ack = false;
	static Map<String,String> m = new HashMap<String, String>();
	private static long ttl = tt*86400;
	private static long duration = duration1*60; 
	private static String endpoint="";
	private static String aud = "";
	private static String email = "";
	static Map<String, String> m1 = new HashMap<>();


	public static void createSubscription() throws Exception {
		ProjectTopicName topic = ProjectTopicName.of(projectId, topicId);
		ProjectSubscriptionName subscription = ProjectSubscriptionName.of(projectId, subscriptionId);
		m.put("name", "ashish");
		Duration d = Duration.newBuilder().setSeconds(ttl).build();
		Duration d1 = Duration.newBuilder().setSeconds(duration).build();
		ExpirationPolicy ex = ExpirationPolicy.newBuilder().setTtl(d).build();
		OidcToken od = OidcToken.newBuilder().setAudience(aud).setServiceAccountEmail(email).build();
		PushConfig p = PushConfig.newBuilder().setPushEndpoint(endpoint).putAllAttributes(m1).setOidcToken(od).
				build();

		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create(SubscriptionAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())) {

			Subscription req = Subscription.newBuilder().setName(subscription.toString())
					.setTopic(topic.toString()).setAckDeadlineSeconds(sec).setRetainAckedMessages(ack)
					.setMessageRetentionDuration(d1).putAllLabels(m).setExpirationPolicy(ex).setPushConfig(p).build();

			Subscription res = subscriptionAdminClient.createSubscription(req);
			System.out.println(res);
		}
	}

	public static void main(String... args) throws Exception {
		createSubscription();
	}
}