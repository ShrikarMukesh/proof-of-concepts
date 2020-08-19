package com.dev.connector;

import java.io.IOException;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.util.Bytes;

public class ListOfTables {
	private static final byte[] TABLE_NAME = Bytes.toBytes("students");
	
	public static void main(String[] args) throws IOException {
		
		Connection connection = GoogleBigtableConnection.getConnection();
		Admin admin = connection.getAdmin();
		System.out.println("**********List of Tables*************");
		HTableDescriptor[] tableList = admin.listTables();
		for (int i = 0; i < tableList.length; i ++) {
			System.out.println(tableList[i].getNameAsString());
		}
		System.out.println("**********List of ColumnFamilies**********");
		HTableDescriptor desc = admin.getTableDescriptor(TableName.valueOf(TABLE_NAME));
		HColumnDescriptor[] hColDesc = desc.getColumnFamilies();
		for (int i = 0; i < hColDesc.length; i ++) {
			System.out.println(hColDesc[i].getNameAsString());
		}

	}
}
