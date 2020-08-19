package com.dev.connector;

import java.io.IOException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class InsertDataToATable {

	private static final byte[] TABLE_NAME = Bytes.toBytes("students");
	private static final byte[] COLUMN_FAMILY_NAME = Bytes.toBytes("email");
	public static void main(String[] args) {

		Connection connection = GoogleBigtableConnection.getConnection();
		try {
			
			Table table = connection.getTable(TableName.valueOf(TABLE_NAME));
			
			String rowKey = "r3" + 1 ;
			Put put = new Put(Bytes.toBytes(rowKey));
			put.addColumn(COLUMN_FAMILY_NAME, "".getBytes(), Bytes.toBytes("Jayadheeraj@gmail.com"));		
				
			table.put(put);
            System.out.println("Data inserted successfully!!!");
		} catch(IOException e) {

			System.err.println("Exception while running InsertDataToATable: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		System.exit(0);
	}
	
}
