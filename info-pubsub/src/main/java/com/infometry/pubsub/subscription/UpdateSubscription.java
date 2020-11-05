package com.infometry.pubsub.subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.protobuf.Duration;
import com.google.protobuf.FieldMask;
import com.google.pubsub.v1.ExpirationPolicy;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.PushConfig.OidcToken;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.UpdateSubscriptionRequest;
import com.infometry.pubsub.PubSubConnection;

public class UpdateSubscription {
	static CredentialsProvider credProvider=PubSubConnection.getCredentials();
	static String projectId=PubSubConnection.getProjectId();
	//private static String topicId = "tony";
	private static String subscriptionId = "stark";
	static int sec = 45;
	private static long duration1=6000;
	private static long duration = duration1*60; 
	private static long tt=50;
	private static long ttl = tt*86400;
	private static boolean ack = true;
	private static String endpoint="https://example.com/push";
	private static String aud = "";
	private static String email = "pubsub-bigtable@inf-pubsub.iam.gserviceaccount.com";
	static Map<String,String> m = new HashMap<String, String>();
	static Map<String,String> m1 = new HashMap<String, String>();
	static List<String> l = new ArrayList<String>();


	public static void main(String... args) throws Exception {
		m1.put("x-goog-version", "v1");
	//	ProjectTopicName topic = ProjectTopicName.of(projectId, topicId);
		ProjectSubscriptionName subscription = ProjectSubscriptionName.of(projectId, subscriptionId);
		Duration d1 = Duration.newBuilder().setSeconds(duration).build();
		Duration d = Duration.newBuilder().setSeconds(ttl).build();
		ExpirationPolicy ex = ExpirationPolicy.newBuilder().setTtl(d).build();
		OidcToken od = OidcToken.newBuilder().setAudience(aud).setServiceAccountEmail(email).build();
		PushConfig p = PushConfig.newBuilder().setPushEndpoint(endpoint).putAllAttributes(m1).setOidcToken(od).
				build();

		m.put("name", "kumar");
		m.put("age", "56");
		m.put("namea", "ashish");
		m.put("lastname", "patel");
		l.add("labels");
		//        l.add("ack_deadline_seconds");
		////        l.add("message_retention_duration");
		//        l.add("expiration_policy");
		//        l.add("retain_acked_messages");
		//        l.add("push_config.push_endpoint");
		//        l.add("push_config.attributes");
		//        l.add("push_config.oidc_token.service_account_email");

		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create(SubscriptionAdminSettings.newBuilder().setCredentialsProvider(credProvider).build())) {

			Subscription req = Subscription.newBuilder().setName(subscription.toString())
					.setAckDeadlineSeconds(sec).setMessageRetentionDuration(d1).setExpirationPolicy(ex).
					setRetainAckedMessages(ack).putAllLabels(m).setPushConfig(p).build();

			System.out.println(req);
			System.out.println("============================================");

			FieldMask updateMask = FieldMask.newBuilder().addAllPaths(l).build();

			System.out.println(updateMask);
			System.out.println("============================================");

			UpdateSubscriptionRequest upRequest = UpdateSubscriptionRequest.newBuilder()
					.setSubscription(req).setUpdateMask(updateMask).build();

			System.out.println(upRequest);
			System.out.println("============================================");

			Subscription response = subscriptionAdminClient.updateSubscription(upRequest);
			System.err.println(response);
		}
	}
}
