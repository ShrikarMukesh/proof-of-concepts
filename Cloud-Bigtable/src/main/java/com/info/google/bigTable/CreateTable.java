package com.info.google.bigTable;

import com.google.cloud.bigtable.hbase.BigtableConfiguration;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;

import org.apache.hadoop.hbase.util.Bytes;
import java.io.IOException;

public class CreateTable {

	 private static final byte[] TABLE_NAME = Bytes.toBytes("User");
	  private static final byte[] COLUMN_FAMILY_NAME = Bytes.toBytes("emails");
	// private static final byte[] COLUMN_NAME = Bytes.toBytes("office");

	 // private static final String[] GREETINGS ={ "pratap,gaurav@infometry.net", "anup_kumar@infometry.net", "shrikar_mukesh@infometry.net" };

	  private static void doHelloWorld(String projectId, String instanceId) {

	    try (Connection connection = BigtableConfiguration.connect(projectId, instanceId)) {

	      Admin admin = connection.getAdmin();

	      HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
	      descriptor.addFamily(new HColumnDescriptor(COLUMN_FAMILY_NAME));
	      
	      
	      
	      
	      print("Create table " + descriptor.getNameAsString());
	      admin.createTable(descriptor);

	     /* Table table = connection.getTable(TableName.valueOf(TABLE_NAME));

	      print("Write some greetings to the table");
	      for (int i = 0; i < GREETINGS.length; i++) {
	        String rowKey = "greeting" + i;

	        Put put = new Put(Bytes.toBytes(rowKey));
	        put.addColumn(COLUMN_FAMILY_NAME, COLUMN_NAME, Bytes.toBytes(GREETINGS[i]));
	        table.put(put);
	      }*/
	      /*

	      String rowKey = "greeting0";
	      Result getResult = table.get(new Get(Bytes.toBytes(rowKey)));
	      String greeting = Bytes.toString(getResult.getValue(COLUMN_FAMILY_NAME, COLUMN_NAME));
	      System.out.println("Get a single greeting by row key");
	      System.out.printf("\t%s = %s\n", rowKey, greeting);

	      Scan scan = new Scan();

	      print("Scan for all greetings:");
	      ResultScanner scanner = table.getScanner(scan);
	      for (Result row : scanner) {
	        byte[] valueBytes = row.getValue(COLUMN_FAMILY_NAME, COLUMN_NAME);
	        System.out.println('\t' + Bytes.toString(valueBytes));
	      }

	      print("Delete the table");
	      admin.disableTable(table.getName());
	      admin.deleteTable(table.getName());*/

	    } catch (IOException e) {
	      System.err.println("Exception while running HelloWorld: " + e.getMessage());
	      e.printStackTrace();
	      System.exit(1);
	    }

	    System.exit(0);
	  }

	  private static void print(String msg) {
	    System.out.println("HelloWorld: " + msg);
	  }

	  public static void main(String[] args) {
	    String projectId = "bigtablepractice-237815";
	    String instanceId = "bigtabledemo";

	    doHelloWorld(projectId, instanceId);
	  }

	 /* private static String requiredProperty(String prop) {
	    String value = System.getProperty(prop);
	    if (value == null) {
	      throw new IllegalArgumentException("Missing required system property: " + prop);
	    }
	    return value;
	  }*/

}
