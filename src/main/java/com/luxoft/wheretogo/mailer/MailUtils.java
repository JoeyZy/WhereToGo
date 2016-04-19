package com.luxoft.wheretogo.mailer;

import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.utils.DateUtils;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;

final public class MailUtils {
    private static final Logger LOGGER = Logger.getLogger(MailUtils.class);
    private final static String NBSP = "&nbsp;";
    private final static String BR = "<br>";
    private final static DateTimeFormatter DAY_MONTH_FORMATTER = DateTimeFormatter.ofPattern("dd MMM");

    private MailUtils(){};

    //TODO: use HTML template instead of html in java
    public static String getMailBody(Map<String, Collection<Event>> upcomingEvents) {
        StringBuilder mailBody = new StringBuilder("<h3>");
        mailBody.append("</h3>");

        try {
            mailBody.append("<ol>");
            for (String category : upcomingEvents.keySet()) {
                mailBody.append("<li><h4>")
                        .append(category)
                        .append("</h4>");

                mailBody.append("<ul>");
                for (Event event : upcomingEvents.get(category)) {
                    mailBody.append("<li>")
                            .append(getEventLink(event))
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

    public static String getEventLink(final Event event) {
        return String.format("<a href='%s%s' target='_blank' title='Click to open it in new window'>%s</a>",
                Mailer.mailProps.getProperty("event.url"),
                event.getId(),
                event.getName());
    }

    public static String getHelloMessage(User user) {
        return String.format("Dear %s %s," + BR + BR
                + "Please find below the list of upcoming events" + BR
                + "and have a pleasant rest of the week :)", user.getFirstName(), user.getLastName());
    }

    /**
     * Here is no sense to show start and end dates if they are equal.
     * In this case only start date + time will be shown
     *
     * @return start date or start/end dates and time
     */
    public static String formatDate(final Event event) {
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
}
