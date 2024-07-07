package com.rodrigo.rabbitmq_consumer.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${broker.queue.patient.name}")
    private String queue;

    // Define a fila do RabbitMQ
    @Bean
    public Queue queue() {
        // Cria uma nova fila com o nome especificado e durável (persiste após reinicialização)
        return new Queue(queue, true);
    }

    // Configura o conversor de mensagens para usar JSON com suporte para Java 8 Date/Time API
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        // Cria um ObjectMapper para converter objetos Java em JSON e vice-versa
        ObjectMapper objectMapper = new ObjectMapper();
        // Registra o módulo JavaTimeModule para suporte a tipos de data/hora do Java 8
        objectMapper.registerModule(new JavaTimeModule());
        // Retorna um conversor de mensagens que usa o ObjectMapper configurado
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    // Configura a fábrica de contêineres de escuta de mensagens para usar o conversor de mensagens configurado
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        // Cria uma fábrica de contêineres de escuta de mensagens simples
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // Define a fábrica de conexões do RabbitMQ
        factory.setConnectionFactory(connectionFactory);
        // Define o conversor de mensagens a ser usado
        factory.setMessageConverter(messageConverter);
        // Retorna a fábrica configurada
        return factory;
    }
}
