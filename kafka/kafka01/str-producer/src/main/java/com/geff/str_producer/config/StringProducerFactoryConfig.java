package com.geff.str_producer.config;

import java.util.HashMap;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.apache.kafka.common.serialization.StringSerializer;

@Configuration
public class StringProducerFactoryConfig {


    @Autowired
    private KafkaProperties kafkaProperties;


    /**
     * ProducerFactory es una interfaz que nos permite crear productores de Kafka.
     * En este caso, estamos creando un productor que enviará mensajes de tipo String.
     * Se configuran algunas propiedades para el productor, como el servidor de arranque y los serializadores para la clave y el valor del mensaje.
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    /**
     * KafkaTemplate es una clase que nos permite enviar mensajes a Kafka de manera sencilla.
     * En este caso, estamos creando un KafkaTemplate que utilizará el ProducerFactory configurado anteriormente.
     * Nos facilita el envío de mensajes a un topic específico de Kafka.
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
