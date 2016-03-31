package com.luxoft.wheretogo.configuration.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.luxoft.wheretogo.mailer.SendDigestJob;

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
	public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job) {

		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		//		schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));

		LOGGER.debug("Setting the Scheduler up");
		schedulerFactory.setJobFactory(springBeanJobFactory());
		schedulerFactory.setJobDetails(job);
		schedulerFactory.setTriggers(trigger);

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

	@Bean
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
}
