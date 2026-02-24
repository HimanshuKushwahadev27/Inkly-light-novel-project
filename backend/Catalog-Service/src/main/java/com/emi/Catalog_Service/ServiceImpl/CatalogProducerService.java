package com.emi.Catalog_Service.ServiceImpl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.emi.events.BookPublishedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatalogProducerService {

	private final KafkaTemplate<String, BookPublishedEvent> kafkaEvent;
	
	public void sendBookCreatedEvent(BookPublishedEvent event) {
		kafkaEvent.send("Book-create-event", event);
	}
}
