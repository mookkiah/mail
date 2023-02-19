package com.example.service.mail;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This approach is not secure and it will work only if your email server has open relay or a proxy 
 * which doesn't expect SSL or authentication.
 * This code should not be in production.
 * 
 * @author mahendran
 *
 */
public class OpenRelayWithAuthentication {

	public static void main(String[] args) {

		// 
		String to = "another@example.com";

		// Sender's email ID needs to be mentioned
		String from = "noreply@example.com";

		// Get system properties
		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", "smtps.local");
		properties.put("mail.smtp.port", "25");
		properties.put("mail.smtp.auth", "true"); // if not authenticated, you will get error like - javax.mail.AuthenticationFailedException: failed to connect, no password specified?

		System.out.println("Please enter password for " + from);
		final String password = new String(System.console().readPassword());
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };
        
		Session session = Session.getDefaultInstance(properties, auth);
		// Keep the debug on so we can solve challenges during development quickly
		session.setDebug(true);

		

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("This is the Subject Line!");

			// Now set the actual message
			message.setText("This is mail sent without SSL communication but authenticated");

			// Send message
			System.out.println("Sending");
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}