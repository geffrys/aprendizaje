package com.geff.str_consumer.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class StrConsumerListener {

    /**
     * Listener para recibir mensajes de tipo String desde el topic "str-topic".
     * Este listener pertenece al grupo "group-1". Un grupo es un conjunto de consumidores que comparten la carga de procesamiento de mensajes.
     * 
     * @param message
     */
    @KafkaListener(groupId = "group-1", topics = "str_topic", containerFactory = "kafkaListenerContainerFactory")
    public void listener1(String message) {
        log.info("Received message: {}", message);
        log.info("Listener1 :::: Recibiendo un mensaje {}", message);
    }

    @KafkaListener(groupId = "group-1", topics = "str_topic", containerFactory = "kafkaListenerContainerFactory")
    public void listener2(String message) {
        log.info("Received message: {}", message);
        log.info("Listener2 :::: Recibiendo un mensaje {}", message);
    }

    @KafkaListener(groupId = "group-2", topics = "str_topic", containerFactory = "kafkaListenerContainerFactory")
    public void listener3(String message) {
        log.info("Received message: {}", message);
        log.info("Listener3 :::: Recibiendo un mensaje {}", message);
    }

    @KafkaListener(groupId = "group-2", topics = "str_topic", containerFactory = "kafkaListenerContainerFactoryWithInterceptor")
    public void listener4(String message) {
        log.info("Received message: {}", message);
        log.info("Listener4 :::: Recibiendo un mensaje {}", message);
    }
}
