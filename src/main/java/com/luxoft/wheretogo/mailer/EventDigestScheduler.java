package com.luxoft.wheretogo.mailer;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class EventDigestScheduler implements ServletContextListener {
	private final static Logger LOGGER = Logger.getLogger(EventDigestScheduler.class);

	private Scheduler scheduler = null;

	@Override
	public void contextInitialized(ServletContextEvent servletContext) {
		LOGGER.debug("Context Initialized");

		try {
			JobDetail job = newJob(SendDigestJob.class)
					.withIdentity("CronQuartzJob", "Group")
					.build();

			Trigger trigger = newTrigger()
					.withIdentity("WeeklyEventDigest", "Digest")
					// TODO: remove cronSchedule("1 * * * * ?") it is for tests only
					.withSchedule(CronScheduleBuilder.cronSchedule("1 * * * * ?"))
					// TODO: uncomment it when development is done
					//					.withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(DateBuilder.MONDAY, 10, 0))
					.startNow()
					.build();

			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		}
		catch (SchedulerException e) {
			LOGGER.error("SchedulerException: " + e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContext) {
		LOGGER.debug("Context Destroyed");
		try {
			scheduler.shutdown();
		}
		catch (SchedulerException e) {
			LOGGER.error("scheduler.shutdown(): " + e);
		}
	}
}
