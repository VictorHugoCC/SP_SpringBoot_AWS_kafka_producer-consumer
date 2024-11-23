package com.mensageria.consumer.consumer;

import com.mensageria.consumer.dto.TransactionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {

    private static final Logger logger = LogManager.getLogger(TransactionConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "transactions", groupId = "consumer-group")
    public void consume(String message) {
        logger.info("Mensagem recebida: {}", message);
        try {
            TransactionDTO transaction = objectMapper.readValue(message, TransactionDTO.class);
            logger.info("Mensagem processada com sucesso: {}", transaction);
        } catch (Exception e) {
            logger.error("Erro ao processar mensagem: {}", message, e);
        }
    }
}
