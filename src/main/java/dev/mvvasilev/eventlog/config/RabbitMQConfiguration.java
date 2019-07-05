package dev.mvvasilev.eventlog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mvvasilev.eventlog.messaging.EventReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    private EventLogProperties properties;

    public RabbitMQConfiguration(EventLogProperties properties) {
        this.properties = properties;
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageConverter messageConverter, MessageListenerAdapter eventListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(properties.getSubmitQueue());
        container.setMessageListener(eventListener);
        return container;
    }

    @Bean
    public Queue submitQueue() {
        return new Queue(properties.getSubmitQueue(), false);
    }

    @Bean
    public Queue publishQueue() {
        return new Queue(properties.getPublishQueue(), false);
    }

    @Bean
    public Exchange exchange() {
        return new TopicExchange(properties.getExchange());
    }

    @Bean
    public Binding submitBinding(Queue submitQueue, Exchange exchange) {
        return BindingBuilder.bind(submitQueue).to(exchange).with(properties.getSubmitRoutingKey()).noargs();
    }

    @Bean
    public Binding publishBinding(Queue publishQueue, Exchange exchange) {
        return BindingBuilder.bind(publishQueue).to(exchange).with(properties.getPublishRoutingKey()).noargs();
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(EventReceiver eventReceiver, MessageConverter messageConverter) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(eventReceiver, "receiveMessage");
        adapter.setMessageConverter(messageConverter);
        return adapter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
