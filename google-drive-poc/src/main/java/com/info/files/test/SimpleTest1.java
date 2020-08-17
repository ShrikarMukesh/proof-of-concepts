package com.info.files.test;

public class SimpleTest1 {
	public static void main(String []args){
		String string1 = SimpleTest1.class.getResource("client_secret_1.json").toString();
		String string2 = SimpleTest1.class.getResource("client_secret_1.json").toExternalForm();
		String string3 = SimpleTest1.class.getResource("client_secret_1.json").getFile();
		String string4 = SimpleTest1.class.getResource("client_secret_1.json").getPath();
		System.out.println(string1);
		System.out.println(string2);
		System.out.println(string3);
		System.out.println(string4);
	}
}
