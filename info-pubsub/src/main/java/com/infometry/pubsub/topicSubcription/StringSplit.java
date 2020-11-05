package com.infometry.pubsub.topicSubcription;

public class StringSplit {
	public static void main(String[] args) {
		String str = "projects/inf-pubsub/subscriptions/Zuul";
		String[] strArray = str.split("/");
		int length = strArray.length;
		String flag = strArray[length-1];
		System.out.println(flag);
		
		/*for(String s :strArray) {
    	System.out.println(s);
    }*/
	}
}
