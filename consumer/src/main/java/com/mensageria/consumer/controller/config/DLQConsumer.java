package com.mensageria.consumer.controller.consumer;

import com.mensageria.consumer.controller.dto.TransactionDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DLQConsumer {

    private static final Logger logger = LogManager.getLogger(DLQConsumer.class);

    @KafkaListener(topics = "transactions-dlq", groupId = "dlq-group")
    public void consumeDLQ(TransactionDTO transaction) {
        logger.error("Mensagem recebida no DLQ: {}", transaction);

        logger.error("Motivo: Esta mensagem foi redirecionada devido a falha no processamento inicial.");

    }

}
