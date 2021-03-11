package com.uc.user.management.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uc.common.response.BaseResponse;
import com.uc.user.management.entity.User;
import com.uc.user.management.request.AddUser;
import com.uc.user.management.response.FetchUsersReponse;
import com.uc.user.management.response.GetUserResponse;
import com.uc.user.management.services.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<BaseResponse> signUp(@Valid @RequestBody AddUser req) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(req));
	}

	@GetMapping("{userId}")
	public ResponseEntity<GetUserResponse> getUser(@PathVariable(required = true) String userId) {
		User user = userService.getUser(userId);
		return Objects.nonNull(user) ? ResponseEntity.status(HttpStatus.OK).body(new GetUserResponse(user))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@GetMapping("fetch")
	public ResponseEntity<FetchUsersReponse> fetchUsers(@RequestParam String ids) {
		return ResponseEntity.ok(userService.fetchUsers(ids));
	}
}
