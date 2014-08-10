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
import org.kbmandir.update.contacts.util.MiscUtils;

/**
 * This class sends email based on configurations!
 * 
 * @author administrator
 * 
 */
public class PostMan {
	private static final String IHF_OUT_REACH_ALIAS = "India Heritage Foundation Sri Krishna Balaram Mandir";

	public static void sendMail(PostManData postManData) throws Exception {

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
			message.setFrom(new InternetAddress(postManData.senderEmailId,
					IHF_OUT_REACH_ALIAS));

			message.setRecipient(
					javax.mail.Message.RecipientType.TO,
					new javax.mail.internet.InternetAddress(
							postManData.contactData.email,
							MiscUtils
									.capitalizeFirstLetter(postManData.contactData.firstName)
									+ " "
									+ MiscUtils
											.capitalizeFirstLetter(postManData.contactData.lastName)));

			// message.setRecipients(Message.RecipientType.TO,
			// InternetAddress.parse("vamshidhar.boda@ihf-usa.org,recorder@ihf-usa.org"));
			message.setSubject("Thank you for Registration");
			message.setText("Hare Krishna "
					+ MiscUtils
							.capitalizeFirstLetter(postManData.contactData.firstName)
					+ ",\n\nThank you for registering with Sri Krishna Balaram Mandir. We hope you had a wonderful time at our temple and participate more in our activities.\nYou will start receiving festival and special event invites of this temple.\n\nFor more information, please visit us at our web-site: http://www.kbmandir.org and visit us on facebook: http://www.facebook.com/kbmandir\n\nAt your service,\nExecutive committee\nSri Krishna Balaram Mandir\nIndia Heritage Foundation\nServing the mission of His Divine Grace A.C.Bhaktivedanta Swami Prabhupada");

			System.out.println("Sending email to :"
					+ postManData.contactData.email);
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}