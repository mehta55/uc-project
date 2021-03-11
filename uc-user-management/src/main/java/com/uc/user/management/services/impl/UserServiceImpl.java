package com.uc.user.management.services.impl;

import static com.uc.common.Constants.SUCCESS;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uc.common.enums.UserRole;
import com.uc.common.exception.BusinessException;
import com.uc.common.response.BaseResponse;
import com.uc.user.management.entity.User;
import com.uc.user.management.repository.UserRepository;
import com.uc.user.management.request.AddUser;
import com.uc.user.management.response.FetchUsersReponse;
import com.uc.user.management.services.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public BaseResponse addUser(AddUser userDto) {

		User user = userRepo.findByEmail(userDto.getMobile());

		if(Objects.nonNull(user)) {
			throw new BusinessException("user already exists");
		}

		user = new User();
		user.setName(userDto.getName());
		user.setMobile(userDto.getMobile());
		user.setEmail(userDto.getEmail());
		user.setRole(UserRole.valueOf(userDto.getRole()));
		user.setPincode(Long.valueOf(userDto.getPincode()));
		userRepo.save(user);
		return new BaseResponse(SUCCESS, "user added");
	}

	@Override
	public User getUser(String userId) {
		return userRepo.findById(Long.valueOf(userId));
	}
	
	@Override
	public FetchUsersReponse fetchUsers(String ids) {
		List<Long> requestedUsers = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
		Map<Long, User> users = userRepo.findAll().parallelStream().filter(user -> requestedUsers.contains(user.getId()))
				.collect(Collectors.toMap(User::getId, Function.identity()));
		return new FetchUsersReponse(users);
	}

}
