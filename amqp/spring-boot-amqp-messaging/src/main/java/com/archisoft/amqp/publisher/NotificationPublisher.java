package com.archisoft.amqp.publisher;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.archisoft.amqp.MessagingApplication;
import com.archisoft.amqp.message.NtfMessage;

@Service
@RestController
@RequestMapping("/ntf")
public class NotificationPublisher {

    private static final Logger log = LoggerFactory.getLogger(NotificationPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public NotificationPublisher(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    
    @RequestMapping(value = "/send/{msg}", method = RequestMethod.GET)
    public void restReceiveNtfEmailMessage(@PathVariable("msg") String now) {
    	
		Map<String, String> params = new HashMap<String, String>();
		params.put("user", "kstest");
		params.put("today", String.valueOf(new Date()));
    	
    	for(int i = 0; i < 100; i++) {
        	System.out.println("now string: " + now);
        	final NtfMessage message = new NtfMessage("hello-world.html", true, true, true, "1@1.com", "4@2.com,5@3.com", "", "subject", true, null, params);
            
            log.info("Sending notification...");
            // rabbitTemplate.convertAndSend(MessagingApplication.EXCHANGE_NAME_NOTIFICATION, MessagingApplication.NTF_ROUTING_KEY_EMAIL, message);
            rabbitTemplate.convertAndSend(MessagingApplication.EXCHANGE_NAME_NOTIFICATION, ".inbox.email.", message);
    	}
    }
    
    @RequestMapping(value = "/sendPojo", method = RequestMethod.POST)
    public void receivePojoMessage(@RequestBody NtfMessage message) {
    	System.out.println("In receivePojoMessage");
    	rabbitTemplate.convertAndSend(MessagingApplication.EXCHANGE_NAME_NOTIFICATION, constructRoutingKey(message), message);
    }
    
    private static final String EMAIL = "email.";
    private static final String INBOX = "inbox.";
    private static final String SMS = "sms.";
    
    private String constructRoutingKey(NtfMessage message) {
    	String routingKey = ".";
    	if(message.isEmail()) {
    		routingKey += EMAIL;
    	}
    	if(message.isInbox()) {
    		routingKey += INBOX;
    	}
    	if(message.isSms()) {
    		routingKey += SMS;
    	}
    	return routingKey;
    }

}
