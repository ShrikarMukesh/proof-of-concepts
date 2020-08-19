package com.metadata.programs;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
public class GenerateServiceAccountKey {

	public static void main(String... args) {

		GoogleCredentials credentials;
		File credentialsPath = new File("src/resource/java/BigTablePractice-49514a2cbd3b.json");  
		try (FileInputStream serviceAccountStream = new FileInputStream(credentialsPath)) {
			credentials = ServiceAccountCredentials.fromStream(serviceAccountStream);
			System.out.println(credentials.createScoped("https://www.googleapis.com/auth/bigtable.admin").refreshAccessToken());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}
