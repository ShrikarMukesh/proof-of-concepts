package com.info.google.bigTable;

import java.util.Scanner;

public class App {

	public static void main( String[] args ) throws Exception {

		System.out.println("Please provide the corresponding operation number tp move forward :");
		System.out.println("\t Create Table : 1");
		System.out.println("\t Read Column : 2");
		System.out.println("\t Write Data : 3");

		Scanner scan = new Scanner(System.in);
		int operNo = scan.nextInt();

		BigTableCRUD crud = new BigTableCRUD("credentials");

		try {
			switch (operNo) {
			case 1 :
				crud.createTable("tableMetadata2");
				break;

			case 2 :
				crud.readColumn ("students", "email", "office");
				break;

			case 3 :
				String []rowKeys = {"companyName", "userName"};
				crud.WriteData("tableMetadata", "C:\\Users\\Dell\\Desktop\\BigTable\\Files\\userDetails.csv", rowKeys);
				break;

			default :
				System.out.println("Invalid Operation Number");
			}
		}
		finally {
			if (scan != null) {
				scan.close();
			}
		}
	}
}
