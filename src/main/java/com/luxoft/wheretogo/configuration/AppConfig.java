package com.luxoft.wheretogo.configuration;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.luxoft.wheretogo.mailer.EventDigest;

@EnableWebMvc
@Configuration
@EnableScheduling
@ComponentScan(basePackages = "com.luxoft.wheretogo")
public class AppConfig extends WebMvcConfigurerAdapter {
	private final static Logger LOGGER = Logger.getLogger(AppConfig.class);

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean
	public EventDigest eventDigest() {
		return new EventDigest();
	}
}
