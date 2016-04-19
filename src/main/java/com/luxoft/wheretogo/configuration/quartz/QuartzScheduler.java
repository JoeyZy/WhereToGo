package com.luxoft.wheretogo.configuration.quartz;

import com.luxoft.wheretogo.mailer.EventNotificationJob;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.luxoft.wheretogo.mailer.SendDigestJob;

import java.util.List;

/**
 * Created by Sergii on 29.03.2016.
 */
@Configuration
public class QuartzScheduler {

	private final static Logger LOGGER = Logger.getLogger(QuartzScheduler.class);

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
		//		schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));

		LOGGER.debug("Setting the Scheduler up");
		schedulerFactory.setJobFactory(springBeanJobFactory());
		schedulerFactory.setJobDetails(job.toArray(new JobDetail[job.size()]));
		schedulerFactory.setTriggers(trigger.toArray(new Trigger[trigger.size()]));

		return schedulerFactory;
	}

	@Bean
	public JobDetailFactoryBean jobDetail() {

		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(SendDigestJob.class);
		jobDetailFactory.setName("CronQuartzJob");
		jobDetailFactory.setDescription("Invoke SendDigestJob...");
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}

	@Bean(name="digestTrigger")
	public CronTriggerFactoryBean trigger(JobDetail job) {

		CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
		trigger.setJobDetail(job);
		//for testing trigger the job every minute
		//trigger.setCronExpression("1 * * * * ?");

		//monday 10:00 AM
		trigger.setCronExpression("0 10 * * 1 ?");
		trigger.setName("WeeklyEventDigest");
		return trigger;
	}


	@Bean(name="notificationTrigger")
	public Trigger notificationTrigger(JobDetail job) {
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("Event notification Trigger")
				.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(8, 0)).build();
		return trigger;
	}

	@Bean(name="notificationJob")
	public JobDetailFactoryBean notificationJob() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(EventNotificationJob.class);
		jobDetailFactory.setName("EventNotificationQuartzJob");
		jobDetailFactory.setDescription("Invoke EventNotificationJob...");
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}
}
