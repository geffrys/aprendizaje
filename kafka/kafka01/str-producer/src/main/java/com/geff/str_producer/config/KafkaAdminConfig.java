package com.geff.str_producer.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;

import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaAdminConfig {


    /**
     * KafkaProperties lo que hace es cargar las propiedades de Kafka
     * desde el archivo application.properties o application.yml.
     */
    @Autowired
    private KafkaProperties kafkaProperties;


    /**
     * Clase que nos facilita la configuracion y administracion de recursos de Kafka. el servidor de arranque, y tambien nos permitira crear los topics.
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        return new KafkaAdmin(configs);
    }


    /**
     * Metodo donde creamos los topicos que vamos a utilizar en nuestra aplicacion.
     * En este caso creamos un topico llamado "str_topic" con 2 particiones y 1 replica.
     */
    @Bean
    public KafkaAdmin.NewTopics topics() {
        return new KafkaAdmin.NewTopics(
            TopicBuilder.name("str_topic").partitions(2).replicas(1).build()
        );
    }

}
