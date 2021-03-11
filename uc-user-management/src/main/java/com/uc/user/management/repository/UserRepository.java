package com.uc.user.management.repository;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.uc.common.enums.UserRole;
import com.uc.user.management.entity.User;

@Repository
public class UserRepository {

	private Long seqId;
	private List<User> users;
	
	public User findById(Long userId) {
		return users.stream().filter(user -> user.getId().equals(userId)).findFirst().orElse(null);
	}

	public User findByEmail(String email) {
		return users.stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
	}
	
	public List<User> findAll() {
		return users;
	}
	
	public void save(User user) {
		user.setId(nextId());
		users.add(user);
	}
	
	private Long nextId() {
		return seqId++;
	}

	@PostConstruct
	private void initializaRepo() {
		users = new LinkedList<>();
		User userAdmin = new User();
		userAdmin.setId(1001l);
		userAdmin.setName("Admin User");
		userAdmin.setEmail("sahil.mehta5555@gmail.com");
		userAdmin.setMobile("9582843302");
		userAdmin.setRole(UserRole.ADMIN);
		userAdmin.setPincode(110044l);
		User userSP = new User();
		userSP.setId(1002l);
		userSP.setName("Sahil Mehta");
		userSP.setEmail("sahil.mehta02@nagarro.com");
		userSP.setMobile("9582843303");
		userSP.setRole(UserRole.SERVICE_PROVIDER);
		userSP.setPincode(110045l);
		User userSR = new User();
		userSR.setId(1003l);
		userSR.setName("Geeta Mehta");
		userSR.setEmail("mehtageeta1811@gmail.com");
		userSR.setMobile("9582843305");
		userSR.setRole(UserRole.SERVICE_RECIEVER);
		userSR.setPincode(110045l);
		
		users.add(userAdmin);
		users.add(userSP);
		users.add(userSR);
		seqId = 1004l;
	}
}
