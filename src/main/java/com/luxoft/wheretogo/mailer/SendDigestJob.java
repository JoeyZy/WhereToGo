package com.luxoft.wheretogo.mailer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.utils.DateUtils;

/**
 * Created by Sergii on 23.03.2016.
 */
@Component
public class SendDigestJob implements Job {

	private final static Logger LOGGER = Logger.getLogger(SendDigestJob.class);

	@Autowired
	private EventDigest digest;

	public static final String NBSP = "&nbsp;";
	public static final DateTimeFormatter DAY_MONTH_FORMATTER = DateTimeFormatter.ofPattern("dd MMM");

	public static final LocalDateTime DIGEST_START_DATE = LocalDateTime.now();
	//TODO: use real end date DIGEST_START_DATE.plusWeeks(1)
	public static final LocalDateTime DIGEST_END_DATE = LocalDateTime.of(2017, 6, 30, 23, 0);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		String recipient = "serg.tanchenko@gmail.com";

		Mailer.sendMail(recipient, getSubject(), getMailBody());
	}

	//TODO: use HTML template instead of html in java
	private String getMailBody() {
		StringBuilder mailBody = new StringBuilder("<h2>");
		mailBody.append(getSubject())
				.append("</h2>");
		try {
			mailBody.append("<hr>");

			//TODO: remove hard-coded date
			Map<String, List<Event>> eventMap = digest.getEvents(DIGEST_START_DATE, DIGEST_END_DATE);

			mailBody.append("<ol>");
			for (String category : eventMap.keySet()) {
				mailBody.append("<li><h3>")
						.append(category)
						.append("</h3>");

				mailBody.append("<ul>");
				for (Event event : eventMap.get(category)) {
					mailBody.append("<li>")
							.append(event.getName())
							.append(NBSP)
							.append(event.getLocation())
							.append(NBSP)
							.append(formatDate(event))
							.append("</li>");
				}
				mailBody.append("</li></ul>");
			}
			mailBody.append("</ol>");
		}
		catch (Exception e) {
			LOGGER.error("Can not generate email body " + e);
		}
		return mailBody.toString();
	}

	/**
	 * Here is no sense to show start and end dates if they are equal.
	 * In this case only start date + time will be shown
	 *
	 * @return start date or start/end dates and time
	 */
	private String formatDate(final Event event) {
		DateTimeFormatter tdf = DateTimeFormatter.ofPattern("dd MMM kk:mm");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("kk:mm");

		LocalDateTime startDate = DateUtils.covertToLocalDateTime(event.getStartDateTime());
		LocalDateTime endDate = DateUtils.covertToLocalDateTime(event.getEndDateTime());

		StringBuilder date = new StringBuilder();
		if (startDate.toLocalDate().equals(endDate.toLocalDate())) {
			date.append(DAY_MONTH_FORMATTER.format(startDate.toLocalDate()))
					.append(NBSP)
					.append(timeFormatter.format(startDate.toLocalTime()))
					.append(NBSP)
					.append("-")
					.append(NBSP)
					.append(timeFormatter.format(endDate.toLocalTime()));
		} else {
			date.append(tdf.format(startDate))
					.append(NBSP)
					.append("-")
					.append(NBSP)
					.append(tdf.format(endDate));
		}
		return date.toString();
	}

	private String getSubject() {
		return String.format("Weekly event digest [%s - %s]",
				DAY_MONTH_FORMATTER.format(DIGEST_START_DATE),
				DAY_MONTH_FORMATTER.format(DIGEST_END_DATE));
	}
}
