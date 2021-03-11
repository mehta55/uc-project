package com.uc.payments.service;

import com.uc.payments.entity.Payment;
import com.uc.payments.request.PaymentRequest;

public interface PaymentService {

	public Long pay(PaymentRequest payment, String userId, String userMail);
	
	public Payment findPayment(String paymentId);
}
