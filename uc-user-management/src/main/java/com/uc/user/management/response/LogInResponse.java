package com.uc.user.management.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.uc.common.response.BaseResponse;
import com.uc.user.management.entity.User;

@JsonInclude(content = Include.NON_NULL)
public class LogInResponse extends BaseResponse {

	private User user;
	private String token;

	public LogInResponse(String status, String message, User user, String token) {
		super(status, message);
		this.user = user;
		this.token = token;
	}

	public LogInResponse(String status, String message) {
		super(status, message);
	}

	public LogInResponse() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
