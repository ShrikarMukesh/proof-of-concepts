package com.UsingPath;


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.shaded.com.google.common.collect.Lists;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.google.api.client.util.PemReader;
import com.google.api.client.util.PemReader.Section;
import com.google.api.client.util.SecurityUtils;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials.Builder;
import com.google.cloud.bigtable.config.BigtableOptions;
import com.google.cloud.bigtable.hbase.BigtableConfiguration;
import com.google.cloud.bigtable.hbase.BigtableOptionsFactory;
public class JSONRead {

	public static void main(String[] args) {

		JSONParser parser = new JSONParser();
		try {
			
			Object obj = parser.parse(new FileReader("D:\\Bigtable\\offshore.json"));
			JSONObject jsonObject = (JSONObject) obj;
			String privateKey = (String) jsonObject.get("private_key");
			String clientEmail = (String) jsonObject.get("client_email");
			String instanceId = generateInstanceId(clientEmail);
			//String instanceId = "offshore";
			String projectId = generateProjectId(clientEmail);
			PrivateKey pk = generatePrivateKey(privateKey);
			
			Builder builder = ServiceAccountCredentials.newBuilder();
			builder = builder.setPrivateKey(pk);
			builder = builder.setScopes(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
			builder = builder.setClientEmail(clientEmail);
			ServiceAccountCredentials sac = builder.build();
			Configuration configuration = new Configuration();
			
			configuration.set(BigtableOptionsFactory.PROJECT_ID_KEY, projectId);
			configuration.set(BigtableOptionsFactory.INSTANCE_ID_KEY, instanceId);
			configuration = BigtableConfiguration.withCredentials(configuration, sac);

			System.out.println(BigtableOptions.getDefaultOptions().getInstanceId());
			try (Connection connection = BigtableConfiguration.connect(configuration)) {
				//Connection connection = ConnectionFactory.createConnection(configuration);
				System.out.println("This is the connection = "+connection);
				Admin admin = connection.getAdmin();
				System.out.println("**********List of Tables*************");
				HTableDescriptor[] listTables = admin.listTables();
				System.out.println(listTables.length);
				for (int i = 0; i < listTables.length; i ++) {
					System.out.println(listTables[i].getNameAsString());
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static String generateInstanceId(String clientEmail) {
		String instanceId;
		instanceId = clientEmail.substring(0,clientEmail.indexOf('@'));
		return instanceId;
	}
	private static String generateProjectId(String serviceAccountID) {

		String projectId = serviceAccountID.substring(serviceAccountID.indexOf("@")+1 ,serviceAccountID.indexOf("."));
		return projectId;
	}
	private static PrivateKey generatePrivateKey(String privateKey) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

		privateKey = privateKey.replaceAll("\\\\n", "\n");
		Reader prKetStr = new StringReader(privateKey);
		Section sec = PemReader.readFirstSectionAndClose(prKetStr,"PRIVATE KEY");
		byte decodedKey[] = sec.getBase64DecodedBytes();
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
		KeyFactory keyFactory = SecurityUtils.getRsaKeyFactory();
		PrivateKey pk = keyFactory.generatePrivate(keySpec);
		return pk;
	}
}