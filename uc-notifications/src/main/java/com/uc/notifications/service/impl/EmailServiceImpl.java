package com.uc.notifications.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.uc.notifications.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	public static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Override
	@HystrixCommand(fallbackMethod = "sendMailFallback")
	public String sendMail(String to, String subject, String body) {
		logger.info("sendMail() started: to {}", to);

		Email from = new Email("sahil.mehta5555@gmail.com");
	    Email toEmail = new Email(to);
	    Content content = new Content("text/plain", body);
	    Mail mail = new Mail(from, subject, toEmail, content);

	    SendGrid sg = new SendGrid("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");
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = sg.api(request);
	      logger.info("sendMail(): status code {}", response.getStatusCode());
	    } catch (IOException ex) {
	      logger.error("sendMail() error", ex);
	      throw new RuntimeException();
	    }
		
	    return "Success";
	}
	
	public String sendMailFallback(String to, String subject, String body) {
		logger.warn("sendMailFallback() started: to {}", to);
		return "Failure";
	}
}
