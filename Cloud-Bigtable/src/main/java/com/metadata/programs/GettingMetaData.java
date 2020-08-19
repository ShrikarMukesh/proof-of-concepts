package com.metadata.programs;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellScanner;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.shaded.com.google.common.collect.Lists;
import org.apache.hadoop.hbase.util.Bytes;
import com.google.api.client.util.PemReader;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.PemReader.Section;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials.Builder;
import com.google.cloud.bigtable.hbase.BigtableConfiguration;
import com.google.cloud.bigtable.hbase.BigtableOptionsFactory;

public class GettingMetaData {
	private static final byte[] TABLE_NAME = Bytes.toBytes("students");
	public static void main(String[] args) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
		String projectId = "info2019";
		String instanceId = "bigtabledemo";
		doHelloWorld(projectId, instanceId);
	}
	private static void doHelloWorld(String projectId, String instanceId) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {

		String privateKey = "-----BEGIN PRIVATE KEY-----\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCJ1godS+xVQdcw\nnYVnHjcs9KNSj22Jdv/2kTSU4+dpYgaRdNKsIdgck1nt/qWVHwjIRR2pSJAUFOaY\n7OmPMdUJ/DMXqubXTR4Qos5GySly0DX9T/MuhNsmOqMMwnTEHFN/mwBDFl3P9Fvj\nlN2lqM6qLV8Kgg/rMdd4bpa9i7gryvQ4ddyiwnFSr5rovccuac65kbWpmPpOE3K0\nSbpsNdoGvHZHkEYzRpQZ7FJ5JhahTKKfCSkl+vFL1Nqe4MF+RdH09tqo3XqspiOo\nCk2DegmKpfXo6PtSuCn2K6HQaZ0fN7KrTFx0KKvan2PStgr0H84BD9NQ6tsLYRQm\nZiMqUGHhAgMBAAECggEAB17FXo7Jy78EFviUSpjTM9Veso5iableC1DVPEEhtYkJ\nH3/c+Djsz63aHxuIvnQY7c8CzJOJhzZdJHgWDsroCKM2du1zr5djPDndYGv3Gjlb\nirCKyP3wAy+7WlY/RMF8PkBzM5Mjh+gpve3+ylHv/4niqSreV6ncCve0Wh0ph1dc\nMAo1lCngI11wg0jJLpj27OsB0xSZ5Uq5rvMX21Q0WF1ythqugFIDaLO0snNwuPpQ\nfUIgb7Se6RiRJ7nAHqVZqkz82kp0tq78xcuHlqIIBCd8W9EjQeSber5tRpX8ZUP9\nKi0DDvUYw5/r7EuB24jY5FTii+h/6vGroWsu/DhyUQKBgQC8ty/LDq2Nxwe++4Do\nCaaSr8hzj5VadrpXtsa353o8NA1LmVJcnMuq7Kbc8iENEPv7GO2WzlvK3r+VeFkF\n0UTx6Y5SWhyQntSD/qH96ViAB5Fnh8/huJl5S9u5KNVmQ5fj0EQ/FVdPbNJmxov1\nS7vMkm3vhFYnWHQSwEjMX071MQKBgQC6+uHb4CkMMzu2mT8MJL9TE6O3/k4fK8ZN\nj+Fc053I1j851dSitnQQS2uJP9aA86aI9H7rHkjjmCUSgOYemyYU/gYfpfFC4ET3\n22Papp5tqSiwvy5mC4aq1PkD/TgINquURgAHo6+ptjbpXxDyphHooHT/puaGU/3j\nkGFQBjvLsQKBgCXJvK05ZDFGkFuCuv47qHoo7seNEcgKjCeyNrpuhIB3n83qhp56\n2yF/v63wYgPeycYu6KxQ5/qIfe5tKc5LaDXUoaYF20BuPXmszD0us4T3fvsvK8Y0\nE69BLF84OPsAP/LjKjdDi7cgwETKLw5QOuusyoyygBni0WNL04fw8buRAoGAN9e3\n3sgq2jbu7ZEFMGZFMdWavtfXkx2qwv1mrCeTZyj9hxNixxnAviEYQ8xqDuc15I9I\n3IWV9Us6PzV6BMgHbpuTTp5r2LJMRs4iG2pJxKyRt3/Rpq5okNq8aEGXG3Tgd48U\ncTOkwgXmjnpTAHUywgwyBSxeit3I5M75EUJbV3ECgYBBCVpsc1e6sjLaJW25Ai9E\nldsMFLL0XefsajH7psKEH0FYZugeZ+VcZ5L7tRJVwiMaOsb8dA9h0uv2hkmTcFLT\n4V9iYuzisJvEyx2IIFiwQuhAjKtHJ2HKB7Nm1UqLwDJtu6aSNC00BkRCMhXJpLUM\nd6sVA5pwz+DL6gpBE1WFGA==\n-----END PRIVATE KEY-----\n";
		String privateKeyId = "3971114c7f9191915da6aa68d4355d9d80b51e31";
		String serviceAccountName = "swiggy";
		StringReader prKetStr = new StringReader(privateKey);
		Section sec = PemReader.readFirstSectionAndClose(prKetStr);
		byte decodedKey[] = sec.getBase64DecodedBytes();
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
		KeyFactory keyFactory = SecurityUtils.getRsaKeyFactory();
		PrivateKey pk = keyFactory.generatePrivate(keySpec);

		Builder builder = ServiceAccountCredentials.newBuilder();
		builder = builder.setPrivateKey(pk);
		builder = builder.setPrivateKeyId(privateKeyId);
		builder = builder.setScopes(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));

		String email = serviceAccountName + "@" + projectId + ".iam.gserviceaccount.com";

		builder = builder.setClientEmail(email);

		ServiceAccountCredentials sac = builder.build();

		Configuration conf1 = new Configuration(false);
		conf1.set(BigtableOptionsFactory.PROJECT_ID_KEY, projectId);

		conf1.set(BigtableOptionsFactory.INSTANCE_ID_KEY, instanceId);

		conf1 = BigtableConfiguration.withCredentials(conf1, sac);

		try (Connection connection = BigtableConfiguration.connect(conf1)) {

			Admin admin = connection.getAdmin();
			
			System.out.println("**********List of Tables*************");
			HTableDescriptor[] listTables = admin.listTables();
			for (int i = 0; i < listTables.length; i ++) {
				System.out.println(listTables[i].getNameAsString());
			}
			System.out.println("**********List of ColumnFamilies**********");
			HTableDescriptor desc = admin.getTableDescriptor(TableName.valueOf(TABLE_NAME));
			HColumnDescriptor[] hColDesc = desc.getColumnFamilies();
			for (int i = 0; i < hColDesc.length; i ++) {
				System.out.println(hColDesc[i].getNameAsString());
			}
			
			System.out.println("**********List of ColumnQualifier**********");
			Table table = connection.getTable(TableName.valueOf(TABLE_NAME));
		
			Scan scan = new Scan();
			ResultScanner scanner = table.getScanner(scan);
			for (Result row : scanner) {

				CellScanner cellScanner = row.cellScanner();
				while (cellScanner.advance()) {
					Cell current = cellScanner.current();
					
					System.out.print(Bytes.toString(current.getQualifierArray()));
				}
				System.out.println();
				//System.out.println('\t' + Bytes.toString(valueBytes));
			}
			
		} catch (IOException e) {
			System.err.println("Exception while running HelloWorld: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		System.exit(0);
	}
}
