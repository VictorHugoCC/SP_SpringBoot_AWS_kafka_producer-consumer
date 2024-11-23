package mensageria.com.producer.controller;

import java.util.UUID;

import mensageria.com.producer.controller.dto.TransactionDTO;
import mensageria.com.producer.service.TransactionProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionProducer producer;

    public TransactionController(TransactionProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/transactions")
    public ResponseEntity<TransactionDTO> create(@RequestBody TransactionDTO transactionInput) {
        TransactionDTO transaction = TransactionDTO.builder()
                .id(UUID.randomUUID().toString())
                .status(transactionInput.getStatus())
                .build();

        log.info("Enviando transação para o Kafka: {}", transaction);
        producer.send(transaction);

        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
}
