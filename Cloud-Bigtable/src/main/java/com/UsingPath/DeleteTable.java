package com.UsingPath;

import java.io.IOException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;


public class DeleteTable {	
	public static void main(String[] args) {
		Connection connection = GoogleBigtableConnection.getConnection();
		System.out.println(connection);
		deleteTable(connection);
	}

	public static void deleteTable(Connection connection) {
		try {
			
			Admin admin = connection.getAdmin();			
			admin.deleteTables("Hello-Bigtable");
			System.out.println("table has been deleted sucessfull");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}		
	}
}
