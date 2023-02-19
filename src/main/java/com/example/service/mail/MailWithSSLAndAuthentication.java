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
public class MailWithSSLAndAuthentication {

	public static void main(String[] args) {

		
		String to = "another@example.com";

		// Sender's email ID needs to be mentioned
		// if this sender not having permission to send email
		// com.sun.mail.smtp.SMTPSendFailedException: 550 5.7.60 SMTP; Client does not have permissions to send as this sender
		String from = "your.mail@example.com";

		// Get system properties
		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", "smtps.example.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");              


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
			message.setText("This mail sent with authenticated user who is allowed to send email from this address - SSL enforced");

			// Send message
			System.out.println("Sending");
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}