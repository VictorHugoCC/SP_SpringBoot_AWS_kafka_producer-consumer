package mensageria.com.producer.controller;

import mensageria.com.producer.controller.dto.TransactionDTO;
import mensageria.com.producer.service.TransactionProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
@Validated
public class TransactionController {
    private static final Logger logger = LogManager.getLogger(TransactionController.class);

    @Autowired
    private TransactionProducer transactionProducer;

    @PostMapping
    public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        if (transactionDTO.getId() == null || transactionDTO.getStatus() == null) {
        logger.info("Receiving transaction for sending to Kafka: {}", transactionDTO);
            return ResponseEntity.badRequest().body("Id or Status cannot be null.");
        }
        transactionProducer.sendMessage(transactionDTO);
        return ResponseEntity.ok().build();
    }
}
