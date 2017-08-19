package com.archisoft.amqp.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archisoft.amqp.MessagingApplication;
import com.archisoft.amqp.email.Email;
import com.archisoft.amqp.email.EmailService;
import com.archisoft.amqp.email.EmailTemplate;
import com.archisoft.amqp.message.NtfMessage;

@Service
public class NotificationListener {

    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);

	@Autowired
	EmailService emailService;
	
    @RabbitListener(queues = MessagingApplication.QUEUE_NTF_EMAIL)
    public void receiveNtfEmail(final NtfMessage message) {
        log.info("Received NTF Email: " + message.isEmail());
//		sendHtmltMail();
        sendHtmlMail(message);
    }
    
    @RabbitListener(queues = MessagingApplication.QUEUE_NTF_INBOX)
    public void receiveNtfInbox(final NtfMessage message) {
        log.info("Received NTF Inbox: " + message.isInbox());
//		sendTextMail();
    }
    
    @RabbitListener(queues = MessagingApplication.QUEUE_NTF_SMS)
    public void receiveNtfSms(final NtfMessage message) {
        log.info("Received NTF SMS: " + message.isSms());
    } 
   
    

	
	private void sendTextMail(String from, String toList, String subject) {

//		String from = "pavan@localhost";
//		String to = "solapure@localhost";
//		String subject = "Java Mail with Spring Boot - Plain Text";

		EmailTemplate template = new EmailTemplate("hello-world-plain.txt");

		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", "Pavan");
		replacements.put("today", String.valueOf(new Date()));

		String message = template.getTemplate(replacements);

		Email email = new Email(from, toList, subject, message);

		emailService.send(email);
	}

	private void sendHtmltMail(String from, String toList, String subject) {

		
//		String from = "pavan@localhost";
//		String to = "solapure@localhost";
//		String subject = "Java Mail with Spring Boot";

		EmailTemplate template = new EmailTemplate("hello-world.html");

		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", "Pavan");
		replacements.put("today", String.valueOf(new Date()));

		String message = template.getTemplate(replacements);

		Email email = new Email(from, toList, subject, message);
		email.setHtml(true);
		emailService.send(email);
	}

	private void sendHtmlMail(NtfMessage ntfmessage) {
		
		String from = ntfmessage.getFrom();
		String to = ntfmessage.getTo();
		String subject = ntfmessage.getSubject();

		EmailTemplate template = new EmailTemplate("hello-world.html");

//		Map<String, String> replacements = new HashMap<String, String>();
//		replacements.put("user", "Pavan");
//		replacements.put("today", String.valueOf(new Date()));

		String message = template.getTemplate(ntfmessage.getContentParam());

		Email email = new Email(from, to, subject, message);
		email.setHtml(true);
		emailService.send(email);
	}
}
