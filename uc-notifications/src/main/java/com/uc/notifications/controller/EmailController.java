package com.uc.notifications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uc.notifications.service.EmailService;

@RestController
@RequestMapping("mail")
public class EmailController {
	
	@Autowired
	private EmailService smsService;

	@PostMapping
	public ResponseEntity<String> sendSMS(@RequestParam String to, @RequestParam String subject, @RequestBody String body) {
		String response = smsService.sendMail(to, subject, body);
		return ResponseEntity.ok().body(response);
	}
	
}
