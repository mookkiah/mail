package com.example.service.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This approach is not secure and it will work only if your email server has open relay or a proxy 
 * which doesn't expect SSL or authentication.
 * This code should not be in production.<br/><br/>
 * 
 * For this you need a SMTP server either in your localhost or in your network.<br/>
 * You may run a development purpose SMTP server like `smtp4dev` or `mailhog`<br/>
 * - docker run -p 3000:80 -p 2525:25 -d --name smtpdev rnwood/smtp4dev<br/>
 * - docker run -p 8025:8025 -p 1025:1025 -d --name mailhog mailhog/mailhog<br/>
 * 
 * @author mahendran
 *
 */
public class OpenRelayMail {

	public static void main(String[] args) {

		
		String to = "email-receipient@localexample.com";

		// Sender's email ID needs to be mentioned
		String from = "dummy.mailbox@example.com";

		// Get system properties
		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", "localhost");
		properties.put("mail.smtp.port", "1025");

		
		Session session = Session.getDefaultInstance(properties);
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
			message.setText("This is actual message");

			// Send message
			System.out.println("Sending");
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}