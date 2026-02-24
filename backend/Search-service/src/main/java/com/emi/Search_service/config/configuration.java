package com.emi.Search_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class configuration {
	private final ConcurrentKafkaListenerContainerFactory<?, ?> concurrentKafkaListenerContainerFactory;
	
	@PostConstruct
	public void setObservationForKafkaTemplate() {
		concurrentKafkaListenerContainerFactory.getContainerProperties().setObservationEnabled(true);
	}

	@Bean
	ObservedAspect observedAspect(ObservationRegistry registry) {
		return new ObservedAspect(registry);
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authorize -> authorize
				.anyRequest().permitAll()
			).build()
			;
	}
}
