package com.macys.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.macys.utils.ConsumerConstants;

@Configuration
public class ConsumerConfig {

    @Bean(name="json")
    Queue jsonQueue() {
        return new Queue(ConsumerConstants.JSON_QUEUE, true);
    }

    @Bean(name="xml")
    Queue xmlQueue() {
        return new Queue(ConsumerConstants.XML_QUEUE, true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(ConsumerConstants.EXCHANGE);
    }

    @Bean
    Binding binding1(DirectExchange exchange) {
        return BindingBuilder.bind(jsonQueue()).to(exchange).with(jsonQueue().getName());
    }

    @Bean
    Binding binding2(DirectExchange exchange) {
        return BindingBuilder.bind(xmlQueue()).to(exchange).with(xmlQueue().getName());
    }

    @Bean(name="jsonTemplate")
    @Primary
    public AmqpTemplate jsonTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setRoutingKey(ConsumerConstants.ROUTING_KEY);
        rabbitTemplate.setDefaultReceiveQueue(jsonQueue().getName());
        return rabbitTemplate;
    }

    @Bean(name="xmlTemplate")
    public RabbitTemplate xmlTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setRoutingKey(ConsumerConstants.ROUTING_KEY);
        rabbitTemplate.setDefaultReceiveQueue(xmlQueue().getName());
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}