package com.luxoft.wheretogo.mailer;

import com.google.common.collect.*;
import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.services.EventsService;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class EventNotificationJob implements Job {
    private final static Logger LOGGER = Logger.getLogger(EventNotificationJob.class);
    private final static DateTimeFormatter DAY_MONTH_FORMATTER = DateTimeFormatter.ofPattern("dd MMM");

    @Autowired
    private EventsService eventsService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("Event notification job is started!");
        List<MailCommand> commands = Lists.newArrayList();
        Multimap<User, Event> events = getPendingToSendEventsMappedToUsers();
        if (events.isEmpty()) {
            LOGGER.info("No events to notify on date: " + LocalDate.now().plusDays(1).toString());
        }
        events.keySet().forEach(key -> commands.add(buildMail(key, events.get(key))));

        commands.forEach(item -> item.execute());
    }

    private MailCommand buildMail(User user, Collection<Event> events) {
        return MailCommand.Builder.builder()
                .withRecipient(user.getEmail())
                .withSubject(getSubject())
                .withBody(MailUtils.getHelloMessage(user)
                        + MailUtils.getMailBody(getGroupedEvents(events)))
                .build();
    }

    private Map<String, Collection<Event>> getGroupedEvents(Collection<Event> events) {
        Multimap<String, Event> multimap = ArrayListMultimap.create();
        events.forEach(item -> multimap.put(item.getCategories().get(0).getName(), item));
        return Multimaps.asMap(multimap);
    }

    private static String getSubject() {
        return String.format("Upcomming event notification - %s",
                DAY_MONTH_FORMATTER.format(LocalDate.now().plusDays(1L)));
    }

    private Multimap<User,Event> getPendingToSendEventsMappedToUsers() {
        Multimap<User, Event> map = ArrayListMultimap.create();
        List<Event> events = getEventsPendingNotification();
        events.forEach((item) -> item.getParticipants().forEach((user) -> map.put(user, item)));

       return map;
    }

    private List<Event> getEventsPendingNotification() {
        final LocalDateTime start = LocalDate.now().plusDays(1).atStartOfDay();
        final LocalDateTime end = LocalDate.now().plusDays(2).atStartOfDay();

        return eventsService.findByPeriod(start, end);
    }

    private static class MailCommand {
        private final String from;
        private final String recipient;
        private final String subject;
        private final String body;

        private MailCommand (Builder builder) {
            from = builder.from;
            recipient = builder.recipient;
            subject = builder.subject;
            body = builder.body;
        }

        public void execute() {
            Mailer.sendMail(recipient, subject, body);
        }

        private static class Builder {
            private String from;
            private String recipient;
            private String subject;
            private String body;

            public static Builder builder() {
                return new Builder();
            }


            public Builder withRecipient(String recipient) {
                this.recipient = recipient;
                return this;
            }

            public Builder withSubject(String subject) {
                this.subject = subject;
                return this;
            }

            public Builder withBody(String body) {
                this.body = body;
                return this;
            }

            public MailCommand build() {
                return new MailCommand(this);
            }

        }
    }
}
