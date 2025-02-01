package com.brainpix.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	private final String HOST;
	private final String USERNAME;
	private final String PASSWORD;

	public MailConfig(@Value("${spring.mail.password}") String HOST,
		@Value("${spring.mail.username}") String USERNAME,
		@Value("${spring.mail.host}") String PASSWORD) {
		this.HOST = HOST;
		this.USERNAME = USERNAME;
		this.PASSWORD = PASSWORD;
	}

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(HOST);
		javaMailSender.setPort(587);
		javaMailSender.setUsername(USERNAME);
		javaMailSender.setPassword(PASSWORD);

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.timeout", 5000);
		properties.put("mail.smtp.starttls.enable", true);
		javaMailSender.setJavaMailProperties(properties);

		return javaMailSender;
	}
}
