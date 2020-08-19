package com.UsingPath;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.shaded.com.google.common.collect.Lists;

import com.google.api.client.util.PemReader;
import com.google.api.client.util.PemReader.Section;
import com.google.api.client.util.SecurityUtils;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials.Builder;
import com.google.cloud.bigtable.hbase.BigtableConfiguration;
import com.google.cloud.bigtable.hbase.BigtableOptionsFactory;

public class GoogleBigtableConnection {
    
	
	public static void main(String[] args) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, InterruptedException {
		getConnection();		
	}
	public static Connection getConnection(){
		Connection connection =null;
		String projectId = "inf-pubsub";  // my-gcp-project-id
		String instanceId = "pubsub-bigtable"; // my-bigtable-instance-id
		try {

			String privateKey = "-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCXjhqazNtpt0w9\\n/cqUjnapq+O/Uvq2rVjQ8HLv+6r5VyrsLI5Xl/Z80wj/YxXlHCBwotKtW3V1p3Vx\\nVsLANw0gMWyo6yEfhfL2vCmSJWn25Tt6BuyT8iqaJfOrapUyTy0qCLY6CNgTh7O4\\nl1N4GXgPbwjf82vJa82SjVQYUJXkYp6orEVN5ZUDK12b/wN9QniC6l+juMGuJnWW\\nnJ8+nMrzXvFAq0HtbjxPgrHKwTBXVibAXxNYXnUvKYNxWzLESJ3F6v+//h0YaCfn\\nKZUA1DExSLbm+CR9oFlsU+8jGf3biTS9DPUDBp/St9xsvBWaQzNM2U0TbRkGMReM\\nfqdC6Ew7AgMBAAECggEALqkkPMvcs+go0587S1CvbR7McF/qa+rkAPQ+1u87nSf+\\nwZcYPhS94YGR8BtAJvlpbXRb+97AQ7iKmpoVPNvWMTa2Vy2JVazGeLvG6sWVIdxC\\n2xedqBEzl6EerASVKdehtqLXA/gsGps5tJf2rW/d/JZ5e4X1Bh8oPDpCDXhmJdnT\\n3GdHt7qRYrpW99nXLBwI4wB09Lv5BVtUYSgC0IeUy2M5ELuNGrx4Oq+gBp+I7wD/\\nA0W+CjAADYf/jRUlW+v3NBWcu3KrcZox1uzHuLp0azwIfqshzx1JpZnre3DYZCoL\\n9l9Aw8w44LZEH4JUcujwp2QU4YEdpmZjmZjZtiRH1QKBgQDKghma5tvZa9aIcJxf\\nrvCFzQnMMNVPGqTCztklaLlksncM+h6JFBNVcPW0J/g4wyxAvcAgj0wZHs2rvQLY\\nDw88euvWLfNe0CDpAphNTlAP8747oDeLnVuTiWbi7W12nSjovGGLxE/lpzHtp3Tm\\nNdMI8rvxlxzWoZBsFnM3eanFXwKBgQC/lnr2yw/VfrvnE0gcN2HAExfzXpOsG//j\\n/RPRuhkM4kbRxYYR0NdFP7myzwzzqgmiW4zLi1C3E5mItjHrKNCeXPVEvwHh7JQP\\n8Qzl27w5riYIUdOqp4EFqtpoqh1fRZo4N7er96U95DSaKuRdDr4VlBsVNyERDTRg\\nYJQGeXWqpQKBgFhe07ukGLZVd8YMlkCu/6jz492mRb+CWfnk+B96BsUpmAgiseaR\\nyyoS5DXc51Yl3Y7W/xt2TPf9WDUkgzQzxitXXMkXPkfWvTXRexLpJMkBTbeJYBPd\\n9FN6t1o5z/Xd+Tcu1cZ/QXpvLruNwtafEFusSVXQ4rXKxVJVrau8vNgzAoGBAJyE\\n7p5S1PPBXvdwLDhJEoXd03JS8Ofp1AtJhXiQxchd0CeubFJ/wG7lmes39kfWwZov\\nmv3rKhz3WTZ0SqHorU7DeOkZ9wrAwVGox7W9569j09R2LSu9Ps4Hf/mECQz9ivmk\\no1xNrGS83DCbHAfedHKileGmMVCB7ilSnXwQnI21AoGBAIE1m5Z378yGqnL5jKGy\\nMU0lbObGNrKzgE4lToa863IrAqNTjgfMDw3UFRu44ijuzf0DZiKG8D/e7ErkAmsE\\nwlwxT+ZtCPpoaoK9ExstTNIorGV/l1yvyk/u7zd8VZyS972S+B5t2p9KhGD1+kXd\\nR6KAWf3sFbd36aC7Gm612YY/\\n-----END PRIVATE KEY-----\\n";
			String client_email = "pubsub-bigtable@inf-pubsub.iam.gserviceaccount.com";
			PrivateKey pk = generatePrivateKey(privateKey);
			
			Builder builder = ServiceAccountCredentials.newBuilder();
			builder = builder.setPrivateKey(pk);
			builder = builder.setScopes(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
			builder = builder.setClientEmail(client_email);
			
			Configuration configuration = new Configuration(false);
			configuration.set(BigtableOptionsFactory.PROJECT_ID_KEY, projectId);
			configuration.set(BigtableOptionsFactory.INSTANCE_ID_KEY, instanceId);	
			Configuration config = BigtableConfiguration.configure(projectId, instanceId);
			config.set(BigtableOptionsFactory.APP_PROFILE_ID_KEY,instanceId);
			connection = ConnectionFactory.createConnection(config);
			//System.out.println(connection);

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
					
		}
		return connection;
		
	}
	private static PrivateKey generatePrivateKey(String privateKey) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
		
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