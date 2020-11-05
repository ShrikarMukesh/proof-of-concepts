package com.basic;

import java.util.HashMap;

public class Main {
	public Main() throws Exception{
		
		/*QueueConsumer consumer = new QueueConsumer("queue");
		Thread consumerThread = new Thread(consumer);
		consumerThread.start();*/
		
		Producer producer = new Producer("queue");
		
		for (int i = 0; i < 100; i++) {
			HashMap<String, Integer> message = new HashMap<String, Integer>();
			message.put("message number", i);
			producer.sendMessage(message);
			System.out.println("Message Number "+ i +" sent.");
		}
	}
	
	public static void main(String[] args) throws Exception{
	  new Main();
	}
}
