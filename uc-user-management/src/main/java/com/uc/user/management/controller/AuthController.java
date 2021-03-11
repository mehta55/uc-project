package com.uc.user.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uc.common.response.BaseResponse;
import com.uc.user.management.request.GenOTPRequest;
import com.uc.user.management.request.LogInRequest;
import com.uc.user.management.response.LogInResponse;
import com.uc.user.management.services.AuthService;

@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("otp")
	public ResponseEntity<BaseResponse> generateOTP(@RequestBody GenOTPRequest req) {
		return ResponseEntity.ok(authService.generateOTP(req.getEmail()));
	}

	@PostMapping("login")
	public ResponseEntity<LogInResponse> validateOTP(@RequestBody LogInRequest req) {
		return ResponseEntity.ok(authService.userLogIn(req));
	}

}
