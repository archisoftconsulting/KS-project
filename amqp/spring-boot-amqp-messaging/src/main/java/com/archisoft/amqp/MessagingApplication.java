package com.archisoft.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableRabbit
@EnableScheduling
public class MessagingApplication implements RabbitListenerConfigurer{

	public static final String EXCHANGE_NAME = "appDirectExchange";
	public static final String QUEUE_GENERIC_NAME = "appGenericQueue";
	public static final String QUEUE_SPECIFIC_NAME = "appSpecificQueue";
	public static final String ROUTING_KEY = "messages.key";

	public static final String QUEUE_KS_TEST1 = "ksTest1Queue";
	
	
	
	
	
	public static final String EXCHANGE_NAME_NOTIFICATION = "ntfExchange";
	public static final String QUEUE_NTF_EMAIL = "ntfEmailQueue";
	public static final String QUEUE_NTF_INBOX = "ntfInboxQueue";
	public static final String QUEUE_NTF_SMS = "ntfSmsQueue";
	public static final String NTF_ROUTING_KEY_EMAIL = "#.email.#";
	public static final String NTF_ROUTING_KEY_INBOX = "#.inbox.#";
	public static final String NTF_ROUTING_KEY_SMS = "#.sms.#";
	
	public static void main(String[] args) {
		SpringApplication.run(MessagingApplication.class, args);
	}

	@Bean
	public TopicExchange ntfExchange() {
		return new TopicExchange(EXCHANGE_NAME_NOTIFICATION);
	}	
	
	@Bean
	public Queue ntfEmailQueue() {
		return new Queue(QUEUE_NTF_EMAIL);
	}
	
	@Bean
	public Queue ntfSmsQueue() {
		return new Queue(QUEUE_NTF_SMS);
	}	

	@Bean
	public Queue ntfInboxQueue() {
		return new Queue(QUEUE_NTF_INBOX);
	}	
	
	@Bean
	public Binding ntfEmailBinding() {
		return BindingBuilder.bind(ntfEmailQueue()).to(ntfExchange()).with(NTF_ROUTING_KEY_EMAIL);
	}

	@Bean
	public Binding ntfInboxBinding() {
		return BindingBuilder.bind(ntfInboxQueue()).to(ntfExchange()).with(NTF_ROUTING_KEY_INBOX);
	}
	
	@Bean
	public Binding ntfSmsBinding() {
		return BindingBuilder.bind(ntfSmsQueue()).to(ntfExchange()).with(NTF_ROUTING_KEY_SMS);
	}
	
	
	
	
	
	
	
	
	@Bean
	public DirectExchange appExchange() {
		return new DirectExchange(EXCHANGE_NAME);
	}

	@Bean
	public Queue appQueueGeneric() {
		return new Queue(QUEUE_GENERIC_NAME);
	}

	@Bean
	public Queue appQueueSpecific() {
		return new Queue(QUEUE_SPECIFIC_NAME);
	}
	
	@Bean
	public Queue ksTest1Queue() {
		return new Queue(QUEUE_KS_TEST1);
	}

	@Bean
	public Binding declareBindingGeneric() {
		return BindingBuilder.bind(appQueueGeneric()).to(appExchange()).with(ROUTING_KEY);
	}

	@Bean
	public Binding declareBindingSpecific() {
		return BindingBuilder.bind(appQueueSpecific()).to(appExchange()).with(ROUTING_KEY);
	}
	
	@Bean
	public Binding declareBindingKsTest1() {
		return BindingBuilder.bind(ksTest1Queue()).to(appExchange()).with(ROUTING_KEY);
	}

	// You can comment all methods below and remove interface's implementation to use the default serialization / deserialization
	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}

	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}

	@Override
	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}

}
