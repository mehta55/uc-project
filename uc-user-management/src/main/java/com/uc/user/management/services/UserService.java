package com.uc.user.management.services;

import com.uc.common.response.BaseResponse;
import com.uc.user.management.entity.User;
import com.uc.user.management.request.AddUser;
import com.uc.user.management.response.FetchUsersReponse;

public interface UserService {

	public BaseResponse addUser(AddUser user);
	
	public User getUser(String userId);

	public FetchUsersReponse fetchUsers(String ids);
}
