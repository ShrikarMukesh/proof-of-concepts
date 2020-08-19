package com.dev.connector;

import java.io.IOException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class DeleteEntireRow {
	private static final byte[] TABLE_NAME = Bytes.toBytes("students");

	public static void main(String[] args) {
		
		Connection connection = GoogleBigtableConnection.getConnection();
		String rowKey = "r21";
		Table table;
		try {
			
			table = connection.getTable(TableName.valueOf(TABLE_NAME));
			byte[] rowKeyId = rowKey.getBytes();
			Delete delete = new Delete(rowKeyId);
			table.delete(delete);
			System.out.println("Entire deleted sucessfully!!!");
		} catch (IllegalArgumentException | IOException e) {
			
			e.printStackTrace();
		}
		
	}
}
