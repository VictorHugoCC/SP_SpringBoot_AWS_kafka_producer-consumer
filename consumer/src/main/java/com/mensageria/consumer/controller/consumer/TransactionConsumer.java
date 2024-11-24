package com.mensageria.consumer.controller.consumer;

import com.mensageria.consumer.controller.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {

    @Autowired
    private KafkaTemplate<String, TransactionDTO> kafkaTemplateForDLQ;

    @KafkaListener(topics = "transactions", groupId = "consumer-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(TransactionDTO transaction, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            System.out.println("Message received from topic: " + topic);
            System.out.println("Message content: " + transaction);

            if ("FAILED".equals(transaction.getStatus())) {
                throw new RuntimeException("Error processing message with status 'FAILED'");
            }

            System.out.println("Message processed successfully!");
        } catch (Exception ex) {
            System.err.println("Error processing message. Redirecting to DLQ. Reason: " + ex.getMessage());

            kafkaTemplateForDLQ.send("transactions-dlq", transaction);
        }
    }
}
