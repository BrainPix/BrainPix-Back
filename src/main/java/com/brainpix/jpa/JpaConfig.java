package com.brainpix.jpa;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.brainpix.alarm.repository.AlarmRepository;
import com.brainpix.message.repository.MessageRepository;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"com.brainpix"}, excludeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AlarmRepository.class),
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MessageRepository.class)})
public class JpaConfig {

}
