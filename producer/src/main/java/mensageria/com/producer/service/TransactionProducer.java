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
            logger.info("Mensagem enviada com sucesso para o tópico 'transactions': {}", transactionDTO);
        } catch (IllegalArgumentException ex) {
            logger.warn("Mensagem inválida. Falha na validação: {}", transactionDTO, ex);
        } catch (Exception ex) {
            logger.error("Erro ao enviar mensagem para o Kafka: {}", transactionDTO, ex);
        }
    }

    private void validateTransaction(TransactionDTO transactionDTO) {
        if (transactionDTO.getId() == null || transactionDTO.getStatus() == null) {
            throw new IllegalArgumentException("Campos obrigatórios não informados!");
        }
    }
}
