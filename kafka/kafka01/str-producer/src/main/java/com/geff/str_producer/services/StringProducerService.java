package com.geff.str_producer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StringProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Método para enviar un mensaje de tipo String al topic "str_topic".
     * Utiliza KafkaTemplate para enviar el mensaje de manera asíncrona.
     * 
     * @param message El mensaje a enviar.
     */
    public void sendMessage(String message) {
        log.info("Producing message: {}", message);
        kafkaTemplate.send("str_topic", message)
        .whenComplete((result, ex) -> {
            if(ex!= null){
                log.error("Error sending message: {}", ex.getMessage());
            } else {
                log.info("Message sent successfully: {}", result.getProducerRecord().value());
                log.info("Message metadata: Partition: {}, Offset: {}", 
                    result.getRecordMetadata().partition(), 
                    result.getRecordMetadata().offset());
            }            
        });
    }
    
}
