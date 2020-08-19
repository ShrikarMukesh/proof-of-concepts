package com.dev.connector;

import java.io.IOException;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellScanner;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class ReadAllColumns {
	private static final byte[] TABLE_NAME = Bytes.toBytes("students");
	
	public static void main(String[] args) throws IOException {

		Connection connection = GoogleBigtableConnection.getConnection();

		Table table = connection.getTable(TableName.valueOf(TABLE_NAME));         
		Scan scan = new Scan();        
		ResultScanner scanner = table.getScanner(scan);
		for (Result row : scanner) {

			System.out.println(new String(row.getRow()));                             
			NavigableMap<byte[], byte[]> familyMap = row.getFamilyMap("email".getBytes());
			//System.out.println(familyMap);             

			Set<Entry<byte[], byte[]>> entrySet = familyMap.entrySet();
			Iterator<Entry<byte[], byte[]>> iterator = entrySet.iterator();

			while (iterator.hasNext()) {

				Entry<byte[], byte[]> next = iterator.next();
				byte[] key = next.getKey();
				byte[] value = next.getValue();
				System.out.println(new String(key) + " : " + new String(value));

			}                             
			CellScanner cellScanner = row.cellScanner();               
			while (cellScanner.advance()) {
				Cell current = cellScanner.current();
				System.out.print(Bytes.toString(current.getFamilyArray()) + " : " + Bytes.toString(current.getQualifierArray()) + " : " + Bytes.toString(current.getValueArray()) + " ");
			}
			System.out.println();
		}
	}
}
