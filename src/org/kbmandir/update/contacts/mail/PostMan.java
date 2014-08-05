package org.kbmandir.update.contacts.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.kbmandir.update.contacts.dto.PostManData;

/**
 * This class sends email based on configurations!
 * 
 * @author administrator
 * 
 */
public class PostMan {

	public static void sendMail(PostManData postManData) {

		final String username = postManData.senderEmailId;
		final String password = postManData.senderPassword;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(postManData.senderEmailId));

			message.setRecipient(javax.mail.Message.RecipientType.TO,
					new javax.mail.internet.InternetAddress(
							postManData.toRecipient));

			// message.setRecipients(Message.RecipientType.TO,
			// InternetAddress.parse("vamshidhar.boda@ihf-usa.org,recorder@ihf-usa.org"));
			message.setSubject("Thank you for Registering!");
			message.setText("Hare Krishna! Thank you for registering Sri Krishna Balarama Mandir!");

			System.out.println("Sending email to :" + postManData.toRecipient);
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}