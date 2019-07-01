package dev.mvvasilev.eventlog.config;

import dev.mvvasilev.eventlog.messaging.EventReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public static final String TOPIC_EXCHANGE_NAME = "event-log-exchange";

    public static final String QUEUE_NAME = "event-log-queue";

    public static final String SUBMIT_EVENT_LOG_ROUTING_KEY = "event.submit.#";

    public static final String PUBLISH_EVENT_LOG_ROUTING_KEY = "event.publish.%s";

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public Exchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(SUBMIT_EVENT_LOG_ROUTING_KEY).noargs();
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(EventReceiver eventReceiver) {
        return new MessageListenerAdapter(eventReceiver, "receive");
    }

}
