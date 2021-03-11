package com.uc.payments.controller;

import static com.uc.common.Constants.Regex.NUMBER;
import static com.uc.common.Constants.UCRequestAttributes.USER_EMAIL;
import static com.uc.common.Constants.UCRequestAttributes.USER_ID;

import java.net.URI;
import java.util.Objects;

import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.uc.common.response.BaseResponse;
import com.uc.payments.entity.Payment;
import com.uc.payments.request.PaymentRequest;
import com.uc.payments.service.PaymentService;

@RestController
@RequestMapping("payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@PostMapping
	public ResponseEntity<BaseResponse> makePayment(@RequestBody PaymentRequest req,
			@RequestAttribute(USER_ID) String userId, @RequestAttribute(USER_EMAIL) String userEmail) {
		
		Long paymentId = paymentService.pay(req, userId, userEmail);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(paymentId).toUri();

		return ResponseEntity.created(location).body(new BaseResponse("success", "payment done"));
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Payment> getBookingDetails(
			@Pattern(regexp = NUMBER, message = "payment id is invalid") @PathVariable String id) {

		Payment response = paymentService.findPayment(id);
		return Objects.nonNull(response) ? ResponseEntity.ok(response) 
				: ResponseEntity.notFound().build();
	}
}
