package com.info.google.bigTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.google.auth.Credentials;
import com.google.cloud.bigtable.hbase.BigtableConfiguration;

public class BigTableCRUD {

	private Credentials cred;
	private String projectId;
	private String instanceId;

	public BigTableCRUD (String credPropBaseName) {

		BigTableCredentials bCred = new BigTableCredentials(credPropBaseName);
		cred = bCred.getCredentials();
		projectId = bCred.getProjectId();
		instanceId = bCred.getInstanceId();

	}

	public void readColumn (String tableName, String columnFamilyName, String columnName) {


		//byte []tableNameBytes = Bytes.toBytes(tableName);
		byte []columnFamilyNameBytes = Bytes.toBytes(columnFamilyName);
		byte []columnNameBytes = Bytes.toBytes(columnName);

		Configuration conf = BigtableConfiguration.configure(projectId, instanceId);
		conf = BigtableConfiguration.withCredentials(conf, cred);

		try (Connection connection = BigtableConfiguration.connect(conf)) {
			//connection.getAdmin().
			Table table = connection.getTable(TableName.valueOf(tableName));
			table.getTableDescriptor().getColumnFamilies()[0].getNameAsString();
			Scan scan = new Scan();

			ResultScanner scanner = table.getScanner(scan);
			for (Result row : scanner) {
				byte[] valueBytes = row.getValue(columnFamilyNameBytes, columnNameBytes);
				System.out.println('\t' + Bytes.toString(valueBytes));
			}
		}
		catch (IOException e) {
			System.err.println("Exception while running : " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void createTable(String tableMetadataProp) {

		Map<String, String[]> tableMetadata = getTableAndColumnFamily(tableMetadataProp);
		String []colFam = (String []) tableMetadata.values().toArray()[0];

		Configuration conf = BigtableConfiguration.configure(projectId, instanceId);
		conf = BigtableConfiguration.withCredentials(conf, cred);
		try (Connection connection = BigtableConfiguration.connect(conf)) {

			Admin admin = connection.getAdmin();

			HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf((String)tableMetadata.keySet().toArray()[0]));

			for (int i = 0; i < colFam.length; i ++) {
				descriptor.addFamily(new HColumnDescriptor(colFam[i]));
			}

			//descriptor.addFamily(new HColumnDescriptor(COLUMN_FAMILY_NAME));
			admin.createTable(descriptor);
		} catch (IOException e) {
			System.err.println("Exception while running : " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void WriteData(String tableMeatadataBaseName, String dataFilePath, String rowKeys[]) throws Exception {

		Map<String, String[]> tableMetadata = getTableAndColumnFamily(tableMeatadataBaseName);
		Map<String, String> columnDetails = getColumnsAndColumnFamily(tableMeatadataBaseName);

		CSVUtils csvUtils = new CSVUtils(dataFilePath);
		String headers [] = csvUtils.getHeaders();
		List<String[]> rows = csvUtils.getRows();

		List<Integer> rowKeyIdx = new ArrayList<>();

		for (int j = 0; j < rowKeys.length; j ++) {
			for (int i = 0; i < headers.length; i ++) {

				if (rowKeys[j].equals(headers[i])) {
					rowKeyIdx.add(i);
					break;
				}
			}
		}

		if (rowKeyIdx.isEmpty()) {
			throw new Exception("RowKey cannot be created");
		}


		Configuration conf = BigtableConfiguration.configure(projectId, instanceId);
		conf = BigtableConfiguration.withCredentials(conf, cred);
		try (Connection connection = BigtableConfiguration.connect(conf)) {

			//Admin admin = connection.getAdmin();
			String tableName = (String)tableMetadata.keySet().toArray()[0];

			Table table = connection.getTable(TableName.valueOf(tableName));

			String row[] = null;
			String rowKey = null;
			String columnFamily = null;
			String column = null;

			for (int i = 0; i < rows.size(); i ++) {
				row = rows.get(i);
				rowKey = "";

				for (int idx = 0; idx < rowKeyIdx.size(); idx ++) {
					rowKey += row [rowKeyIdx.get(idx)];
					if (idx < rowKeyIdx.size() - 1) {
						rowKey += "#";
					}
				}

				Put put = new Put(Bytes.toBytes(rowKey));

				for (int j = 0; j < row.length; j ++) {
					columnFamily = columnDetails.get(headers[j]);
					column = headers[j];

					//Put put = new Put(Bytes.toBytes(rowKey));
					put.addColumn(columnFamily.getBytes(), column.getBytes(), Bytes.toBytes(row[j]));		
				}

				table.put(put);
			}
		} 
		catch (IOException e) {
			System.err.println("Exception while running : " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void readByRowKey(String tableName, String columnFamilyName, String columnName,String rowKey) {

		byte []columnFamilyNameBytes = Bytes.toBytes(columnFamilyName);
		byte []columnNameBytes = Bytes.toBytes(columnName);

		Configuration conf = BigtableConfiguration.configure(projectId, instanceId);
		conf = BigtableConfiguration.withCredentials(conf, cred);

		try (Connection connection = BigtableConfiguration.connect(conf)) {

			//Admin admin = connection.getAdmin();
			Table table = connection.getTable(TableName.valueOf(tableName));

			Result getResult = table.get(new Get(Bytes.toBytes(rowKey)));
			String greeting = Bytes.toString(getResult.getValue(columnFamilyNameBytes, columnNameBytes));
			System.out.printf("\t%s = %s\n", rowKey, greeting);
		} catch (IOException e) {
			System.err.println("Exception while running: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void deleteRow(String tableName, String rowKey) {

		Configuration conf = BigtableConfiguration.configure(projectId, instanceId);
		conf = BigtableConfiguration.withCredentials(conf, cred);

		try (Connection connection = BigtableConfiguration.connect(conf)) {

			//Admin admin = connection.getAdmin();
			Table table = connection.getTable(TableName.valueOf(tableName));

			byte[] rowKeyId = rowKey.getBytes();
			Delete delete = new Delete(rowKeyId);
			table.delete(delete);


		} catch (IOException e) {
			System.err.println("Exception while running: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	private Map<String, String[]> getTableAndColumnFamily (String tableMeatadataBaseName) {

		ResourceBundle rBundle = ResourceBundle.getBundle(tableMeatadataBaseName);
		String tableName = rBundle.getString("tableName");
		String columnFamily = rBundle.getString("columnFamily");

		String columnFam[] = columnFamily.split(",");

		Map<String, String []> tableDetails = new LinkedHashMap<>(1);
		tableDetails.put(tableName, columnFam);

		return tableDetails;
	}

	private Map<String, String> getColumnsAndColumnFamily (String tableMeatadataBaseName) {

		ResourceBundle rBundle = ResourceBundle.getBundle(tableMeatadataBaseName);
		String columns = rBundle.getString("columns");

		String columnQuals[] = columns.split(",");
		Map<String, String> columnDetails = new LinkedHashMap<>();

		String []colAndFam = null;

		for (int i = 0; i < columnQuals.length; i ++) {
			colAndFam = columnQuals [i] . split(":");

			columnDetails.put(colAndFam[1], colAndFam[0]);
		}

		return columnDetails;
	}
}
