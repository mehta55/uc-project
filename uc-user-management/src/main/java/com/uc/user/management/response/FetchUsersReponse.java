package com.uc.user.management.response;

import java.util.Map;

import com.uc.user.management.entity.User;

public class FetchUsersReponse {

	private Map<Long, User> users;

	public FetchUsersReponse(Map<Long, User> users) {
		super();
		this.users = users;
	}

	public Map<Long, User> getUsers() {
		return users;
	}

	public void setUsers(Map<Long, User> users) {
		this.users = users;
	}

}
