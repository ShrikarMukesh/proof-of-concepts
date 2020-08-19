package com.UsingPath;

import java.io.IOException;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.util.Bytes;

public class CreateTable1 {
	public static void main(String[] args) {
		Connection connection = GoogleBigtableConnection.getConnection();
		System.out.println(connection);
		create(connection);
	}

	private static final byte[] TABLE_NAME = Bytes.toBytes("Hello-Bigtable");
	private static final byte[] COLUMN_FAMILY_NAME = Bytes.toBytes("cf1");
	private static final byte[] COLUMN_NAME = Bytes.toBytes("greeting");
	//private static final String GREETINGS = "Hello World!";

	public static String create(Connection connection) {
		try {
			
			Admin admin = connection.getAdmin();
			HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
			descriptor.addFamily(new HColumnDescriptor(COLUMN_FAMILY_NAME));
			descriptor.addFamily(new HColumnDescriptor(COLUMN_NAME));
			//descriptor.addFamily(new HColumnDescriptor(GREETINGS));
			admin.createTable(descriptor);
			
			System.out.println("table has been created");
			
		} catch (IOException e) {
			
			return "Table exists.";
		}
		return "Create table " + Bytes.toString(TABLE_NAME);
	}
}
