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


    @Bean
    Queue fooQueue() {
        return new Queue("foo", true);
    }

    @Bean
    FanoutExchange fooFanoutExchange() {
        return new FanoutExchange("fooFanoutExchange");
    }

    @Bean
    DirectExchange fooDirectExchange() {
        return new DirectExchange("fooDirectExchange");
    }


    @Bean
    Binding bindingFooQueue(Queue fooQueue, FanoutExchange fooFanoutExchange) {
        return BindingBuilder.bind(fooQueue).to(fooFanoutExchange);
    }@Bean
    Binding bindingFooQueue2(Queue fooQueue, DirectExchange fooDirectExchange) {
        return BindingBuilder.bind(fooQueue).to(fooDirectExchange).with("foo.key");
    }


}
