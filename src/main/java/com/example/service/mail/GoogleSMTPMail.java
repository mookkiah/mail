package com.example.service.mail;

import java.io.Console;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * - Go to https://myaccount.google.com/apppasswords.<br/>
 * - In `Select the app and device you want to generate the app password for`, Choose others and give a name for the app.<br/>
 * - Click `GENERATE`<br/>
 * - You will see the app password. Keep it safe and use when this program prompting you.
 * 
 * 
 * @author mahendran
 *
 */
public class GoogleSMTPMail {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		//System.out.println("Enter username");
		//final String username = scanner.nextLine();
		final String username = "your.gmail@gmail.com";
		System.out.println("Enter password");
		Console console = System.console();
		// WARNIING: If you are running in IDE, your password maybe visible as you type
		final char[] password = console.readPassword();
		scanner.close();
		
		String to = "other.email@example.com";

		// Sender's email ID needs to be mentioned. This should be an address which username allowed to send mail on behalf of.
		String from = "your.gmail@gmail.com";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.put("mail.smtp.host", "smtp.gmail.com"); // Not these smtp.google.com or other address from the output of `nslookup -q=mx google.com` 
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true"); // TLS
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		
		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, new String(password));
			}
		});
		session.setDebug(true);

		

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Sent from JAVA code");
			message.setText("There is nothing to say....");

			// Send message
			System.out.println("Sending");
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}