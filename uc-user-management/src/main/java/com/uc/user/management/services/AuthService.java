package com.uc.user.management.services;

import com.uc.common.response.BaseResponse;
import com.uc.user.management.request.LogInRequest;
import com.uc.user.management.response.LogInResponse;

public interface AuthService {

	public BaseResponse generateOTP(String mobile);

	public LogInResponse userLogIn(LogInRequest req);
	
}
