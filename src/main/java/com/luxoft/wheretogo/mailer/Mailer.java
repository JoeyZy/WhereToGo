package com.luxoft.wheretogo.mailer;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * Created by Sergii on 24.03.2016.
 */
public class Mailer {

	private final static Logger LOGGER = Logger.getLogger(Mailer.class);
	private final static String SMTP_PROPS_FILE_NAME = "smtp-server.properties";
	private final static String USERNAME = null;
	private final static String PASSWORD = null;
	private static Properties smtpProps = new Properties();

	static {
		try {
			smtpProps.load(Mailer.class.getClassLoader().getResourceAsStream(SMTP_PROPS_FILE_NAME));
		}
		catch (IOException | NullPointerException e) {
			LOGGER.error("Can't load properties from file " + SMTP_PROPS_FILE_NAME, e);
		}
	}

	public static void sendMail(String recipientsEmail, String subject, String text) {
		LOGGER.debug(String.format("Sending email... [Subject='%s'; to='%s'; text='%s']", subject, recipientsEmail, text));

		try {
			Message message = new MimeMessage(getSession());
			message.setFrom(new InternetAddress("from-email@gmail.com"));

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipientsEmail));

			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);

			LOGGER.debug(String.format("Email has been sent [Subject='%s'; to='%s'; text='%s']", subject, recipientsEmail, text));
		}
		catch (MessagingException e) {
			LOGGER.error(String.format("Email has NOT been sent [Subject='%s'; to='%s'; text='%s']", subject, recipientsEmail, text), e);
		}
	}

	private static Session getSession() {
		Session session = Session.getInstance(smtpProps,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(USERNAME, PASSWORD);
					}
				});

		//session.setDebug(LOGGER.isDebugEnabled());

		return session;
	}
}
