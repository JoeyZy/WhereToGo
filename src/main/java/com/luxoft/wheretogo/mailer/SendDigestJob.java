package com.luxoft.wheretogo.mailer;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by Sergii on 23.03.2016.
 */
public class SendDigestJob implements Job {

	//	private final static Logger LOGGER = Logger.getLogger(SendDigestJob.class);

	private String recipient = "serg.tanchenko@gmail.com";
	private String s = "Testing Subject";
	private String s1 = "It works!";

	public void execute(JobExecutionContext context) throws JobExecutionException {
		Mailer.sendMail(recipient, s, s1);
	}

}
