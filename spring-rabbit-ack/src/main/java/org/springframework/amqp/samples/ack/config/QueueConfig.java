package org.springframework.amqp.samples.ack.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    public static final String FOO_ROUTER_KEY = "foo";

    @Bean
    Queue fooQueue() {
        return new Queue("foo", true);
    }

    @Bean
    DirectExchange fooDirectExchange() {
        return new DirectExchange("fooDirectExchange");
    }

    @Bean
    Binding bindingFooQueue(Queue fooQueue, DirectExchange fooDirectExchange) {
        return BindingBuilder.bind(fooQueue).to(fooDirectExchange).with(FOO_ROUTER_KEY);
    }


}
