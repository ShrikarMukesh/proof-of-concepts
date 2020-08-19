package com.metadata.programs;

import com.google.cloud.bigtable.hbase.BigtableConfiguration;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
public class BigtableConnect {

	public static String projectId;
	public static String instanceId;
	public static String appProfileId = "default";
	public static Connection connection = null;
	public static void main(String... args) throws IOException {

		projectId = "info2019";  
		instanceId = "bigtabledemo"; 
		//connect();
		System.out.println("connected using projectId and instanceId");
		connectWithConfiguration();
		//System.out.println("connectedWithConfiguration");
		if (args.length > 2) {
			appProfileId = args[2];    
		}
	}
	// bigtable_connect_with_configuration]
	public static void connectWithConfiguration() throws IOException {
		Configuration config = HBaseConfiguration.create();
		connection = ConnectionFactory.createConnection(config);
		System.out.println(connection.toString());
	}

	// bigtable_connect using projectId and instanceId
	public static void connect() throws IOException {

		connection = BigtableConfiguration.connect(projectId, instanceId);
		System.out.println("This is connection object = "+connection.toString());
		Admin admin = connection.getAdmin();
		System.out.println("**********List of Tables*************");
		HTableDescriptor[] listTables = admin.listTables();
		for (int i = 0; i < listTables.length; i ++) {
			System.out.println(listTables[i].getNameAsString());
		}
	}

	// bigtable_connect_app_profile
	public static void connectWithAppProfile() throws IOException {
		connection = BigtableConfiguration.connect(projectId, instanceId, appProfileId);
	}
}
