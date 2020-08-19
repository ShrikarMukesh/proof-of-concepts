package com.dev.connector;

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
        String projectId = "offshore-254107";  // my-gcp-project-id
        String instanceId = "offshore"; // my-bigtable-instance-id
        try {
            String privateKey = "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCeOfOdP0qtaAtX\nBdPVjCiHR8DogGa4DNomqTzt1Yx1J7p7178Exukaep1/Lb3isgLc/mzsdOMbgJcy\nG6tze/EnCP54SrKRmSv6qd5s8EOHlyqZCL2CqLrMUCCBwYKeffpszFrRGScg5120\nZ6hyb2/2PQVFZHieMP6dLu3L2M349B+kUIh6k6V3n+UL65YbKPnDxeKSKExDztnn\nox69f9FqHdOeDMw6V4xhG/V8mXrlAGb8EKD5bHq6TAjb7sx0fF8cjCyCjiZSQ8zW\naDvSl+WVUzIoC2vO2omM5RcXus7Qh4+zengYvkag3+o/mHByz+CdVEgLAiOcavNO\nn72Xz26hAgMBAAECggEAA82NGz1EsbiboSqnhe1ETMVpj7evqwJ1ZyglKerlNoGk\ncUqPsZvdnsB4svBUrTNYb0WsNN4LAVkMs+xx/qi+yeXk8TDhFtpxcf5i4AfBpRYk\niiC+0U+gjGhBR8JwvcIgYpIM0r7B2oAabUtv2IRGKCByVScji7B4xiq1Fh5ZYP+P\nati+lagF8oO0+tQmgXXxZaWhDkJWPqBerDD9lbXeHU9XxbvpRPR3tf/1KqSzHbSI\nBsBWVOuCMvYz0BE6aSExJjXq1Pa/runIY50LaPMjLSxH0Pf5QpFBmKWj4CxSDalD\nIl3S0ahbmfJgcW8hKsT6uQ2DYOQGZ98IBfwXWoFZEwKBgQDMEQzXwahA2sGYOhQH\nUMUJZ3ZZPKK88dTtAFqP9FDmI3abDuwQxu+cbSZ5EvB5ahPdZgGjJtkrn4IjTD+F\nEYhc2qZDGZ9+uUnSuZiix/OozvBVo8Tx18LBp6hLxcbMxWvebtvrStcjiWsjjN3S\n0LzfDq8vaCE7tzfm5SwrMQnuzwKBgQDGfmh8Wh/RFyQ2d5ETt0Z21VTd5Bk1/jRU\nqgNVHKV0F0/aCi6E3XDEkJN8Jww176wkEnABAQ5D11ubkdhRwcSyT+sbBpDh48T4\n/eNlCQkkssFJ6HNLo75JHdaQWtU6Mqh/TG1iR9jmvwwoG2GzE00wvNgcxON1QKfW\niHmAzmanjwKBgQCTxRD4t7o/5Gjl88CHH5ze13Xi3r61daULTgNGVNO0IyAC3oZh\nC719F6kRaX6LXR4vZ2tuGnaOWT4ik1c3499L5eUZl9dgK4pitpTp40L1DY0JZsL5\noZTdKKv82qW/qIIZG0vTEWnCyQK1Ro9m1lK+b2K6QHw4Qs+POoqGaEdaDwKBgF9c\nz9oeena8BXvdiefM/5+L8pAATFRyYrXiUGONtbfGk2iPx7WxpcHqK9FT+TvYf/18\nli8FUNMrHinpseHllBiO6Mc9KlI8WZ++aodaBfiJoO9fLDVE3+DE4xbLbPHe8LDo\nCQ1Us1J4quCB9zhPp6AoujNoLFdizr/1JVrKzurlAoGBAJJ63xUjQ3+oAZEBDgje\nv6VsOBiUvNY9X0qbCXsyjnFuFAFx9/UCIqOjvqXE0Jrf9Kt/gu40VdGS18T1R8vC\nD5FlA+0zS9CMB2jjKgLxtD6dMAmeDPYNhasG3LbZOxx2buUNjNB1mIOWDv+emIhH\nlI3cJTYQLeugys/GUqdR/utz\n-----END PRIVATE KEY-----\n";
            String client_email = "offshore@offshore-254107.iam.gserviceaccount.com";
            PrivateKey pk = generatePrivateKey(privateKey);
            
            Builder builder = ServiceAccountCredentials.newBuilder();
            builder = builder.setPrivateKey(pk);
            builder = builder.setScopes(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
            builder = builder.setClientEmail(client_email);
            ServiceAccountCredentials sac = builder.build();
            
            Configuration configuration = new Configuration(false);
            configuration.set(BigtableOptionsFactory.PROJECT_ID_KEY, projectId);
            configuration.set(BigtableOptionsFactory.INSTANCE_ID_KEY, instanceId);
            configuration = BigtableConfiguration.withCredentials(configuration, sac);
            
            connection = BigtableConfiguration.connect(configuration);
            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
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
