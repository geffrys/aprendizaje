package com.geff.str_consumer.config;

import java.util.HashMap;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.RecordInterceptor;

import lombok.extern.log4j.Log4j2;


@Configuration
@Log4j2
public class StringConsumerConfig {

    /**
     * Configuración del consumidor de Kafka para recibir mensajes de tipo String.
     * Utiliza las propiedades de Kafka proporcionadas por Spring Boot.
     * Se configura el deserializador para las claves y valores como String.
     */
    private final KafkaProperties kafkaProperties;
    public StringConsumerConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    /**
     * Se configura el factory para los consumidores de Kafka.
     * Se utiliza la configuración de KafkaProperties proporcionada por Spring Boot.
     * Se especifica el tipo de clave y valor como String.
     * Se utiliza para recibir mensajes de tipo String.
     * @return ConsumerFactory<String, String>
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configs);
    }

    /**
     * Se configura el factory para los listeners de Kafka.
     * Se utiliza el ConsumerFactory configurado anteriormente.
     * Se especifica el tipo de clave y valor como String.
     * Se utiliza para recibir mensajes de tipo String.
     * @param consumerFactory
     * @return ConcurrentKafkaListenerContainerFactory<String, String>
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactoryWithInterceptor(ConsumerFactory<String, String> consumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(consumerFactory);
        factory.setRecordInterceptor(interceptor());
        return factory;
    }

    /**
     * Interceptor para manipular los mensajes recibidos.
     * Este interceptor se utiliza para interceptar los mensajes antes de que sean procesados por el listener.
     * En este caso, se interceptan los mensajes que contienen la palabra "Hola" y se registra un mensaje en el log.
     * @return
     */
    private RecordInterceptor<String, String> interceptor() {
        return (record, consumer) -> {
            if( record.value().contains("hola")) {
                log.info("Interceptor :::: Interceptando un mensaje que contiene 'Hola' : {}", record.value());
            }
            return record;
        };
    }
    
}
