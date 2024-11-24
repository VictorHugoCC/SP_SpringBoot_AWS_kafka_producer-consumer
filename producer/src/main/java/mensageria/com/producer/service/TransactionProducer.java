package mensageria.com.producer.service;

import mensageria.com.producer.controller.dto.TransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer {

    private static final Logger logger = LoggerFactory.getLogger(TransactionProducer.class);
    private final KafkaTemplate<String, TransactionDTO> kafkaTemplate;

    public TransactionProducer(KafkaTemplate<String, TransactionDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(TransactionDTO transactionDTO) {
        try {
            validateTransaction(transactionDTO);
            kafkaTemplate.send("transactions", transactionDTO);
            logger.info("Message successfully sent to the 'transactions' topic:: {}", transactionDTO);
        } catch (IllegalArgumentException ex) {
            logger.warn("Invalid message. Validation failed: {}", transactionDTO, ex);
        } catch (Exception ex) {
            logger.error("Error while sending message to Kafka: {}", transactionDTO, ex);
        }
    }

    private void validateTransaction(TransactionDTO transactionDTO) {
        if (transactionDTO.getId() == null || transactionDTO.getStatus() == null) {
            throw new IllegalArgumentException("Required fields not provided!");
        }
    }
}
