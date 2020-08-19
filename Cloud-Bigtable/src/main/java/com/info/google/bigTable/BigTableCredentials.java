package com.info.google.bigTable;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ResourceBundle;

import org.apache.hadoop.hbase.shaded.com.google.common.collect.Lists;

import com.google.api.client.util.PemReader;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.PemReader.Section;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials.Builder;

public class BigTableCredentials {

	private Credentials cred;
	private String projectId;
	private String instanceId;

	public BigTableCredentials (String baseName) {

		ResourceBundle resBundle = ResourceBundle.getBundle(baseName);
		projectId = resBundle.getString("projectId");
		instanceId = resBundle.getString("instanceId");
		String privateKey = resBundle.getString("privateKey");
		//System.out.println(privateKey);
		//String privateKeyId = resBundle.getString("privateKeyId");

		try {
			PrivateKey pKey = privateKeyFromPkcs8(privateKey);
			String email = instanceId + "@" + projectId + ".iam.gserviceaccount.com";

			Builder builder = ServiceAccountCredentials.newBuilder().setPrivateKey(pKey).setScopes(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform")).setClientEmail(email);
			cred = builder.build();
            System.out.println(cred);
			if(cred.getRequestMetadata().get("clientId") == null) {
				System.out.println("Connection Not Success");
			}
			else {
				System.out.println("Connection Success");
			}

		} catch (IOException e) {

			e.printStackTrace();
			System.exit(1);
		}
	}

	public Credentials getCredentials () {
		return cred;
	}
	public String getProjectId () {
		return projectId;
	}
	public String getInstanceId () {
		return instanceId;
	}

	private static PrivateKey privateKeyFromPkcs8(String privateKeyPkcs8) throws IOException {

		Reader reader = new StringReader(privateKeyPkcs8);
		Section section = PemReader.readFirstSectionAndClose(reader, "PRIVATE KEY");
		if (section == null) {
			throw new IOException("Invalid PKCS#8 data.");
		}
		byte[] bytes = section.getBase64DecodedBytes();
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
		Exception unexpectedException;
		try {
			KeyFactory keyFactory = SecurityUtils.getRsaKeyFactory();
			return keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
			unexpectedException = exception;
		}
		throw new IOException("Unexpected exception reading PKCS#8 data", unexpectedException);
	}
}
