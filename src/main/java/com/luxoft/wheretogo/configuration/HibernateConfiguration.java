package com.luxoft.wheretogo.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource(value = {"classpath:application.properties"})
public class HibernateConfiguration {

	@Autowired
	private Environment environment;

	@Bean
	public LocalSessionFactoryBean sessionFactory() throws PropertyVetoException {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.luxoft.wheretogo.models");
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public DataSource dataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setJdbcUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUser(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		dataSource.setMinPoolSize(Integer.valueOf(environment.getRequiredProperty("c3po.minPoolSize")));
		dataSource.setMaxPoolSize(Integer.valueOf(environment.getRequiredProperty("c3po.maxPoolSize")));
		dataSource.setCheckoutTimeout(Integer.valueOf(environment.getRequiredProperty("c3po.timeout")));
		dataSource.setMaxStatements(Integer.valueOf(environment.getRequiredProperty("c3po.max_statements")));
		dataSource.setIdleConnectionTestPeriod(Integer.valueOf(environment.getRequiredProperty("c3po.idle_test_period")));
		return dataSource;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.hbm2ddl.import_files", environment.getRequiredProperty("hibernate.hbm2ddl.import_files"));
		properties.put("hibernate.hbm2ddl.import_files_sql_extractor",
		environment.getRequiredProperty("hibernate.hbm2ddl.import_files_sql_extractor"));
		return properties;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory s) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(s);
		return txManager;
	}
}
