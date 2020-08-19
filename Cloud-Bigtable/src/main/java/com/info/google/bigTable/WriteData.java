package com.info.google.bigTable;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.shaded.com.google.common.collect.Lists;
import org.apache.hadoop.hbase.util.Bytes;
import com.google.api.client.util.PemReader;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.PemReader.Section;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials.Builder;
import com.google.cloud.bigtable.hbase.BigtableConfiguration;

public class WriteData {

	 private static final byte[] TABLE_NAME = Bytes.toBytes("User");
	  private static final byte[] COLUMN_FAMILY_NAME = Bytes.toBytes("address");
	 //private static final byte[] COLUMN_NAME = Bytes.toBytes("office");

	  //private static final String[] GREETINGS ={ "pratap,gaurav@infometry.net", "anup_kumar@infometry.net", "shrikar_mukesh@infometry.net" };

	  private static void doHelloWorld(String projectId, String instanceId) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {


			String privateKey = "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDaqMRFxFJF4S1k\nq4WPv7dueoWpOTyWu4aynbt15xM9Pudxg4omuIzuGEEilrBohZbjxkSjj8U3Prk/\ncjrmMHn6ggkQkiDvD+h+wS7rYgIXeg3DJN8VPiQME/zyRvrxgI+PmhnpBZ6kOwno\nKGwi3dV2E/J1vPFdPa313EEnE08QuyzJoNCfJZRff/1BYXHliAOXVfj/jSlw9GE6\nvtFWYRWB2/nheMRr9WFN9wyY/lXDfPga3Mm7rmwQt5lwSzTmsNxC/OVehrePeGqr\nm0cUR6APY0OaCH2d0cj27kaEo0AMyhCcrOBXZoEWeqKPzoSK+lRtfhigjMfpCTJf\nD4CPNpPJAgMBAAECggEAHgH/PZvdtp1af3BSmhGzeMPzx++E8yK1L5oZ+epGZ6dU\nV+23bezx2lMVzUtk1lNPC99P2CqI5DYFckGY97I7azehStbjEivaulqEt+TxaItl\njBxpSR8ID2hG1HENS0tbaFe7qpEM3nkDuQqjHH3ZJ6VBJwtRDHzEfPNHNbeeqCrm\nzZD4y07lPT9BcEwC2tQvLYdYZWhmncO/y5+7ZZiz2EpczuRcsUsRIx5oU7+8DVOp\nKcTlKjj+8BnUQLckjsVv++hB4dqQteIybpgUN+yBiG05r0EI7wvcQfMJDzBOjTVK\nEpSJ9VSEwDPVn/SnB3zcRCG8oDXYCdkR1GfsHbINoQKBgQD4DvXQmRvcsvEjIJK8\nsoOBMsdEQZb9qm9szIWO+psXGidd7k5UGfLN8Yqm7s6z1S+kLPIQG8htH6wQBf67\nM2rcW72AJQkZmrqSqBEDXcLh5iGfG+qyGB9EabUnnmUpNK5qKVDDLLK44/JQI7YP\nJclPpaI3PdXT1DskKHvRAUCtJQKBgQDhqNs09SO3q4TtYdVkc/+dCvCQviqoJ+ko\nl+hWTU+opR+oRGyK6j1BRGnLVOhsN/k7o0i93L+mrHu6wVtpMaayYzo4lzyImH6I\nPJ+IMiSb3VnyeAE2XyKgZPBle3dATPu8Fic2rHtdfvf13Xxk4evcDL2ET800atjE\nVkrN9t801QKBgHaZgIHZkm60x4IKAt5hY2qUQp2p1m013cxhBMJVo20qPgQ0+hJP\nzb0VbTtfFkj49qxCKQH2pp8182g91YrfjIoK+tGX43/wcJ/J50h6vcCP7SdLi6zW\nx6xH9isZGjjXTUTIuCp45oJryeDzEgqwAE4qXSKYV/HNmPEywfAt7yRZAoGAGzxs\nKLols3dqNdSHEvbFNUkqu+2X7G/UvmeHTVNLeAlmBsMrbqDS/9uoATigTN98n2wX\nzYKlRxKuZF4OEGZDvmgnkMkAL2RocT0P7DpRHNbckl1VmCBnZ4CHW1hJjmM8RvlN\nhlXPFfvLJcuzxeFWvFp1TVDsbZUv0JKxuKZBTnkCgYEAslwnzpLHqYOIrjnwjtRy\nAB4NRBGljGziizBkKFzr3cB2M8c/DAM9jf7rLEAEjTI95/v+m5fjuuFoEFDxz3oF\nB5Ey2qtJZgH0JAOKQMlx9cKQmUHh1dg8PAvYN5Uof5lBG4cRhxalA8sMgaO9hkC4\nnVA1CW2kl+t5dyfn+KJYtZA=\n-----END PRIVATE KEY-----\n";
			String privateKeyId = "91b9690ce9c19ed8621ea2bcf50ce8f427922cc3";

			//byte []pKeyByte = privateKey.getBytes();

			//KeyFactory kf = KeyFactory.getInstance("RSA");

			//PrivateKey pk = kf.generatePrivate(new PKCS8EncodedKeySpec(pKeyByte));
			//System.out.println(pk.getEncoded());

			StringReader prKetStr = new StringReader(privateKey);
			//char cBuf[] = new char[20000];
			//prKetStr.read(cBuf);
			//System.out.println(String.valueOf(cBuf));
			
			//System.out.println(prKetStr.read(String.valueOf(cBuf)));
			
			//PemReader pem = new PemReader(prKetStr);
			//Section sec1 = pem.readNextSection();
			//System.out.println(sec1.getBase64DecodedBytes());
			//pem.close();
			
			Section sec = PemReader.readFirstSectionAndClose(prKetStr);

			/*byte by[] = sec.getBase64DecodedBytes();
			System.out.println(sec.getBase64DecodedBytes());
			
			for (int i = 0; i < by.length; i ++) {
				System.out.print(by[i]);
			}*/
			
			byte decodedKey[] = sec.getBase64DecodedBytes();
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
			KeyFactory keyFactory = SecurityUtils.getRsaKeyFactory();
			PrivateKey pk = keyFactory.generatePrivate(keySpec);

			System.out.println("=======================");
			
			//PrivateKey p = SecurityUtils.get
			
			//String privateKeyId = "91b9690ce9c19ed8621ea2bcf50ce8f427922cc3";

			
			Builder builder = ServiceAccountCredentials.newBuilder();
			builder = builder.setPrivateKey(pk);
			builder = builder.setPrivateKeyId(privateKeyId);
			builder = builder.setScopes(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
			
			String email = instanceId + "@" + projectId + ".iam.gserviceaccount.com";
			
			builder = builder.setClientEmail(email);
			
			//System.out.println(builder);
			
			ServiceAccountCredentials sac = builder.build();
			
			//ServiceAccountCredentials sac = ServiceAccountCredentials.newBuilder().setPrivateKey(pk).setPrivateKeyId(privateKeyId).setScopes(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform")).build();


			//Configuration conf = BigtableConfiguration.withCredentials(null, credentials).;
			Configuration conf = BigtableConfiguration.configure(projectId, instanceId);
			conf = BigtableConfiguration.withCredentials(conf, sac);

			try (Connection connection = BigtableConfiguration.connect(conf)) {

				//Admin admin = connection.getAdmin();

				// HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
				// descriptor.addFamily(new HColumnDescriptor(COLUMN_FAMILY_NAME));

				// print("Create table " + descriptor.getNameAsString());
				// admin.createTable(descriptor);

				Table table = connection.getTable(TableName.valueOf(TABLE_NAME));

	     // HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
	     // descriptor.addFamily(new HColumnDescriptor(COLUMN_FAMILY_NAME));

	     // print("Create table " + descriptor.getNameAsString());
	     // admin.createTable(descriptor);


	      print("Write some greetings to the table");
	     // for (int i = 0 ; i < 3; i++) {
	        String rowKey = "greeting" + 1 ;

	        Put put = new Put(Bytes.toBytes(rowKey));
	        put.addColumn(COLUMN_FAMILY_NAME, "".getBytes(), Bytes.toBytes("HYD"));
	        
	       // table.batch
	        
	        table.put(put);
	  //    }
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

	  public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
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
