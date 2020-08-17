package com.shrikar;


import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;

// https://developers.google.com/identity/protocols/OAuth2#scenarios
// https://developers.google.com/api-client-library/java/google-api-java-client/oauth2
class GApiGateway {

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  
    public static Drive drive;
    private static String applicationName;
    private static GoogleClientSecrets clientSecrets;
    /**
     * Global instance of the {@link FileDataStoreFactory}. The best practice is to
     * make it a single globally shared instance across your application.
     */
    private static FileDataStoreFactory dataStoreFactory;
    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport httpTransport;

    /**
     * Be sure to specify the name of your application. If the application name
     * is {@code null} or blank, the application will log a warning. Suggested
     * format is "MyCompany-ProductName/1.0".
     */
    public static void init(String applicationName, InputStream clientSecret, File dataStoreDirectory) throws
            GeneralSecurityException, IOException {
        GApiGateway.applicationName = applicationName;
        GApiGateway.clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(clientSecret));

        httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        dataStoreFactory = new FileDataStoreFactory(dataStoreDirectory);
        
       
    }

    /**
     * Authorizes the installed application to access user's protected data.
     */
    public static void authorize(String userID, boolean driveAPI) throws IOException {
        // load client secrets

        // set up authorization code flow
        Collection<String> scopes = new ArrayList<String>();
        
        if (driveAPI)
            scopes.add(DriveScopes.DRIVE_APPDATA);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY,
                clientSecrets, scopes).setDataStoreFactory(dataStoreFactory).build();
        // authorize
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()) {
            // Override open browser not working well on Linux and maybe other
            // OS.
            protected void onAuthorization(AuthorizationCodeRequestUrl authorizationUrl) throws java.io.IOException {
              
            }
        }.authorize(userID);

        
        if (driveAPI)
            drive = new Drive.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(applicationName).build();

    }

    public static void closeSession() {
        
        drive = null;
    }

}