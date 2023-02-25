package com.example.service.mail;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import org.apache.commons.codec.binary.Base64;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import static javax.mail.Message.RecipientType.TO;
import static javax.mail.Message.RecipientType.CC;;

/** 
 * - Go to https://console.cloud.google.com/apis/credentials<br/>
 * - In `Select Create Credentials and Click Help me choose`<br/>
 * - `Select Gmail API`  and `Select User data` then Click Next<br/>
 * - Fill up the App Information and `Click Save and Continue`<br/>
 * - Click `Add or Remove Scopes` and `Add Gmail API and Select Gmail Compose` Click `Update` and `Save and Continue`<br/>
 * - Select Application Type `Desktop app` and give a name for the app<br/>
 * - Click `Create` and Download JSON File. Keep it safe and use file path.
 *
 */
public class GoogleMail {

    private static final String MY_EMAIL = "your.gmail@gmail.com";
    private final Gmail service;
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
    
    public GoogleMail() throws Exception {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        service = new Gmail.Builder(httpTransport, jsonFactory, getCredentials(httpTransport, jsonFactory)).build();
    }

    private static Credential getCredentials(final NetHttpTransport httpTransport, GsonFactory jsonFactory)
            throws IOException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(GoogleMail.class.getClassLoader().getResourceAsStream("../credentials.json")));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, SCOPES).build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public void sendMail(String subject, String message) throws Exception {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(MY_EMAIL));
        email.addRecipient(TO, new InternetAddress(MY_EMAIL));
        email.addRecipient(CC, new InternetAddress("your.gmail@gmail.com"));
        email.setSubject(subject);
        email.setText(message);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message msg = new Message();
        msg.setRaw(encodedEmail);

        try {
            msg = service.users().messages().send("", msg).execute();
            System.out.println("Message id: " + msg.getId());
            System.out.println(msg.toPrettyString());
        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to send message: " + e.getDetails());
            } else {
                throw e;
            }
        }
    }

    public static void main(String[] args) throws Exception {
    	
        new GoogleMail().sendMail("Sent from JAVA code", "There is nothing to say....");
    }

}