package com.luxoft.wheretogo.mailer;

import java.time.LocalDateTime;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Sergii on 23.03.2016.
 */
@Component
public class SendDigestJob implements Job {

	@Autowired
	private EventDigest digest;
	private final static Logger LOGGER = Logger.getLogger(SendDigestJob.class);

	private String recipient = "serg.tanchenko@gmail.com";
	private String s = "Testing Subject";
	private String s1 = "It works!";

	public void execute(JobExecutionContext context) throws JobExecutionException {
		digest.getEvents(LocalDateTime.now(), LocalDateTime.of(2017, 6, 15, 23, 0));

		//		Mailer.sendMail(recipient, s, s1);
	}

}
