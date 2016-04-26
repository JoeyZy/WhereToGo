package com.luxoft.wheretogo.configuration.quartz;

import com.luxoft.wheretogo.mailer.EventNotificationJob;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.luxoft.wheretogo.mailer.SendDigestJob;

import java.util.List;

/**
 * Created by Sergii on 29.03.2016.
 */
@Configuration
public class QuartzSchedulerConfiguration {

	private final static Logger LOGGER = Logger.getLogger(QuartzSchedulerConfiguration.class);

	@Autowired
	private ApplicationContext applicationContext;



	@Bean
	public SpringBeanJobFactory springBeanJobFactory() {
		AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
		LOGGER.debug("Configuring Job factory");

		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean
	public SchedulerFactoryBean scheduler(List<Trigger> trigger, List<JobDetail> job) {

		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		schedulerFactory.setTriggers(trigger.toArray(new Trigger[trigger.size()]));
		schedulerFactory.setJobDetails(job.toArray(new JobDetail[job.size()]));
		schedulerFactory.setJobFactory(springBeanJobFactory());
		LOGGER.debug("Setting the Scheduler up");

		return schedulerFactory;
	}


	@Bean
	@Qualifier("digestJob")
	public JobDetail jobDetail() {
		JobDetail jd = JobBuilder.newJob(SendDigestJob.class)
				.withIdentity("DIGEST_JOB", "MAILER")
				.withDescription("Invoke SendDigestJob...")
				.storeDurably(true)
				.build();
		return jd;
	}

	@Bean
	public Trigger trigger(@Qualifier("digestJob") JobDetail job) {
		Trigger trigger = TriggerBuilder.newTrigger()
				.forJob("DIGEST_JOB", "MAILER")
				.withIdentity("DIGEST_TRIGGER", "MAILER")
			///	.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(30))
				.withSchedule(CronScheduleBuilder.monthlyOnDayAndHourAndMinute(
						DateBuilder.MONDAY, 10, 0))
				.build();

		return trigger;
	}


	@Bean
	@Qualifier("notificationJob")
	public JobDetail notificationJob() {
		JobDetail jd = JobBuilder.newJob(EventNotificationJob.class)
				.withIdentity("NOTIFICATION_JOB", "MAILER")
				.withDescription("Invoke EventNotificationJob...")
				.storeDurably(true)
				.build();
		return jd;
	}

	@Bean
	public Trigger notificationTrigger(@Qualifier("notificationJob")JobDetail job) {
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("NOTIFICATION_TRIGGER", "MAILER")
				.forJob("NOTIFICATION_JOB", "MAILER")
				//.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(30))
				.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(8, 0))
				.build();
		return trigger;
	}

}
