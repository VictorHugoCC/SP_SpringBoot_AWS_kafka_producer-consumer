package com.mensageria.consumer.consumer;


import com.mensageria.consumer.dto.TransactionDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {

    private static final Logger log = LoggerFactory.getLogger(TransactionConsumer.class);

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, TransactionDTO> record) {
        log.info("Received message from partition: {}", record.partition());
        log.info("Received message: {}", record.value());
    }
}
