package com.mensageria.consumer.controller.config;

import com.mensageria.consumer.controller.dto.TransactionDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TransactionDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TransactionDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(1000L, 3))); // Configurar retry
        return factory;
    }

    @Bean
    public DefaultKafkaConsumerFactory<String, TransactionDTO> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(TransactionDTO.class, false)) // Type headers desativados
        );
    }

    @Bean
    public KafkaTemplate<String, TransactionDTO> kafkaTemplateForDLQ() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // Serializador JSON

        ProducerFactory<String, TransactionDTO> producerFactory = new DefaultKafkaProducerFactory<>(configs);
        return new KafkaTemplate<>(producerFactory);
    }


    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }
}
