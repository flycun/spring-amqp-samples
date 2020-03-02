package org.springframework.amqp.samples.ack.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.samples.ack.config.QueueConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class SendMessageJobs {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private AtomicInteger count = new AtomicInteger();

    @Scheduled(fixedDelay = 2000)
    public void sendMessage() {
        setupCallbacks();
        String json = "{\"foo\" : \"value\" }";
        log.info("send : {} ", count.incrementAndGet());
        rabbitTemplate.convertAndSend("fooDirectExchange", QueueConfig.FOO_ROUTER_KEY, json, new CorrelationData("" + count.get()));

    }

    private void setupCallbacks() {
        /*
         * Confirms/returns enabled in application.properties - add the callbacks here.
         */
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.warn("Returned : {},replyCode: {},replyText: {},exchange/rk: {}/{}", message, replyCode, replyText, exchange, routingKey);
        });

        this.rabbitTemplate.setConfirmCallback((correlation, ack, reason) -> {
            if (correlation != null) {
                log.info("Received  {} for correlation: {},reason: {}", ack ? " ack " : " nack ", correlation, reason);
            }
        });

    }
}
