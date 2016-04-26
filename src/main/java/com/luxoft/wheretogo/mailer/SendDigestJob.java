package com.luxoft.wheretogo.mailer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.User;

/**
 * Created by Sergii on 23.03.2016.
 */
@Component
public class SendDigestJob implements Job {

	private final static Logger LOGGER = Logger.getLogger(SendDigestJob.class);
	private final static DateTimeFormatter DAY_MONTH_FORMATTER = DateTimeFormatter.ofPattern("dd MMM");

	@Autowired
	private EventDigest digest;

	public static final LocalDateTime DIGEST_START_DATE = LocalDateTime.now();
	public static final LocalDateTime DIGEST_END_DATE = DIGEST_START_DATE.plusWeeks(1);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		Map<String, Collection<Event>> upcomingEvents = digest.getEvents(DIGEST_START_DATE, DIGEST_END_DATE);
		// we should not send digest if no events.
		if (upcomingEvents.isEmpty()) {
			LOGGER.warn(String.format("No events found for period: %s - %s. Any emails have been sent.",
					DAY_MONTH_FORMATTER.format(DIGEST_START_DATE), DAY_MONTH_FORMATTER.format(DIGEST_END_DATE)));
			return;
		}

		String subject = getSubject();
		String mailBody = MailUtils.getMailBody(upcomingEvents);

		for (User user : digest.getUsers()) {
			String helloMessage = MailUtils.getHelloMessage(user);
			Mailer.sendMail(user.getEmail(), subject, helloMessage + mailBody);
		}
	}

	public static String getSubject() {
		return String.format("Weekly event digest [%s - %s]",
				DAY_MONTH_FORMATTER.format(DIGEST_START_DATE),
				DAY_MONTH_FORMATTER.format(DIGEST_END_DATE));
	}


}
