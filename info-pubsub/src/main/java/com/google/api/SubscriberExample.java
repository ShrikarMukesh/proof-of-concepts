package com.google.api;


import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;

public class SubscriberExample {
  // use the default project id
  private static final String PROJECT_ID = ServiceOptions.getDefaultProjectId();

  static class MessageReceiverExample implements MessageReceiver {

    @Override
    public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
    	
      System.out.println(
          "Message Id: " + message.getMessageId() + " Data: " + message.getData().toStringUtf8());
      // Ack only after all work for the message is complete.
      consumer.ack();
    }
  }

  /** Receive messages over a subscription. */
  public static void main(String... args) throws Exception {
	  
    // set subscriber id, eg. my-sub
    String subscriptionId = "Neycer";
    ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(
        PROJECT_ID, subscriptionId);
    Subscriber subscriber = null;
    try {
      // create a subscriber bound to the asynchronous message receiver
      subscriber =
          Subscriber.newBuilder(subscriptionName, new MessageReceiverExample()).build();
      subscriber.startAsync().awaitRunning();
      // Allow the subscriber to run indefinitely unless an unrecoverable error occurs.
      subscriber.awaitTerminated();
    } catch (IllegalStateException e) {
      System.out.println("Subscriber unexpectedly stopped: " + e);
    }
  }
}