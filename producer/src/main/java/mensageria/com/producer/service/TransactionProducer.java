package mensageria.com.producer.service;

import mensageria.com.producer.controller.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionProducer {

    @Value("${topic.name}")
    private String topic;

    private final KafkaTemplate<String, TransactionDTO> kafkaTemplate;

    public TransactionProducer(KafkaTemplate<String, TransactionDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(TransactionDTO transaction) {
        try {
            kafkaTemplate.send(topic, transaction);
            log.info("Mensagem enviada com sucesso: {}", transaction);
        } catch (Exception e) {
            log.error("Erro ao enviar mensagem: {}", e.getMessage());
        }
    }
}
