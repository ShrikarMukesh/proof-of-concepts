package com.using.environmentvariables;

import java.io.IOException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import com.google.cloud.bigtable.hbase.BigtableConfiguration;

public class ReadVerUsingEnv {

	private static final byte[] TABLE_NAME = Bytes.toBytes("students");
	private static final byte[] COLUMN_FAMILY_NAME = Bytes.toBytes("email");
	private static final byte[] COLUMN_NAME = Bytes.toBytes("name");
	//COLUMN Qualifier name
	
	private static void doHelloWorld(String projectId, String instanceId) {

		try (Connection connection = BigtableConfiguration.connect(projectId, instanceId)) {
			
			Table table = connection.getTable(TableName.valueOf(TABLE_NAME));
			Scan scan = new Scan();
			//print("Scan for all greetings:");
			ResultScanner scanner = table.getScanner(scan);
			for (Result row : scanner) {
				byte[] valueBytes = row.getValue(COLUMN_FAMILY_NAME, COLUMN_NAME);
				System.out.println('\t' + Bytes.toString(valueBytes));
			}
		} catch (IOException e) {
			System.err.println("Exception while running HelloWorld: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		System.exit(0);
	}

	public static void main(String[] args) {
		String projectId = "offshore-254107";
		String instanceId = "offshore";
		doHelloWorld(projectId, instanceId);
	}
}