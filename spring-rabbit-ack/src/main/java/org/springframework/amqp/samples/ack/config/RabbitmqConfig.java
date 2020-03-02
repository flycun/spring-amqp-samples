package org.springframework.amqp.samples.ack.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

@Slf4j
@Configuration
public class RabbitmqConfig {
    @Bean
    public MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    SimpleRabbitListenerContainerFactory simpleRabbitListenerContainer(final ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory listenerFactory = new SimpleRabbitListenerContainerFactory();
        listenerFactory.setConnectionFactory(connectionFactory);
        listenerFactory.setConcurrentConsumers(1);
        listenerFactory.setMaxConcurrentConsumers(5);
        return listenerFactory;
    }
}
