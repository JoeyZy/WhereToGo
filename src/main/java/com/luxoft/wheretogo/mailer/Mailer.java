package com.luxoft.wheretogo.mailer;

import java.io.IOException;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
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
	private final static String MAIL_PROPS_FILE_NAME = "email.properties";

	public static Properties mailProps = new Properties();
	private static Properties smtpProps = new Properties();

	static {
		try {
			smtpProps.load(Mailer.class.getClassLoader().getResourceAsStream(SMTP_PROPS_FILE_NAME));
			mailProps.load(Mailer.class.getClassLoader().getResourceAsStream(MAIL_PROPS_FILE_NAME));
		}
		catch (IOException | NullPointerException e) {
			LOGGER.error("Can't load properties", e);
		}
	}

	public static void sendMail(String recipientsEmail, String subject, String htmlContent) {
		LOGGER.debug(String.format("Sending email... [Subject='%s'; to='%s']", subject, recipientsEmail));
		final String username = (String) mailProps.get("email.username");
		final String password = (String) mailProps.get("email.password");

		try {
			Session session = getSession(username, password);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipientsEmail));

			message.setSubject(subject);
			message.setContent(htmlContent, "text/html; charset=utf-8");
			Transport.send(message);

			LOGGER.debug(String.format("Email has been sent [Subject='%s'; to='%s'; htmlContent='%s']", subject, recipientsEmail, htmlContent));
		}
		catch (IllegalArgumentException | AuthenticationFailedException afe) {
			LOGGER.error(String.format("Wrong login(email) or password [login=%s; password=%s]", username, password), afe);
		}
		catch (Exception e) {
			LOGGER.error(String.format("Email has NOT been sent [Subject='%s'; to='%s'; htmlContent='%s']", subject, recipientsEmail,
					htmlContent), e);
		}
	}

	private static Session getSession(final String username, final String password) {
		if (username == null || password == null || username.isEmpty() || username.isEmpty()) {
			throw new IllegalArgumentException("Can't get a new session. Username/password can not be null or empty");
		}

		Session session = Session.getInstance(smtpProps,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		//session.setDebug(LOGGER.isDebugEnabled());
		return session;
	}
}
