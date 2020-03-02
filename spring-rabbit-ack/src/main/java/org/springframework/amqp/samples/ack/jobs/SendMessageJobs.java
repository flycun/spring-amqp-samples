package org.springframework.amqp.samples.ack.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class SendMessageJobs {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private AtomicInteger count=new AtomicInteger();

    @Scheduled(fixedDelay = 2000)
    public void sendMessage(){
        setupCallbacks();
        rabbitTemplate.convertAndSend("fooDirectExchange","foo.key.1",new Foo("foo"));
        log.info("send : {} ",count.incrementAndGet());
    }

    private void setupCallbacks() {
        /*
         * Confirms/returns enabled in application.properties - add the callbacks here.
         */
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.warn("Returned : {},replyCode: {},replyText: {},exchange/rk: {}/{}", message, replyCode, replyText, exchange, routingKey);
        });

    }

    public static class Foo {

        private String foo;

        public Foo() {
            super();
        }

        public Foo(String foo) {
            this.foo = foo;
        }

        public String getFoo() {
            return this.foo;
        }

        public void setFoo(String foo) {
            this.foo = foo;
        }

        @Override
        public String toString() {
            return "Foo [foo=" + this.foo + "]";
        }

    }

}
