package com.uc.user.management.response;

import com.uc.common.response.BaseResponse;
import com.uc.user.management.entity.User;

public class GetUserResponse extends BaseResponse {

	private User user;
	
	public GetUserResponse(User user) {
		super("success", "user found");
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
