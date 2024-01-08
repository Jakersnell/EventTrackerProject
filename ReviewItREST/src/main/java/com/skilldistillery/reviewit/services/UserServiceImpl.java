package com.skilldistillery.reviewit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.entities.User;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.exceptions.RestServerException;
import com.skilldistillery.reviewit.exceptions.TokenInvalidException;
import com.skilldistillery.reviewit.exceptions.UserDoesNotExistException;
import com.skilldistillery.reviewit.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AuthService authService;

	@Override
	public User getUserById(int userId, String auth) throws RestServerException {
		User user = userRepo.findById(userId).orElseThrow(EntityDoesNotExistException::new);
		if (!user.isEnabled() && !authService.userIsAdmin(auth)) {
			throw new EntityDoesNotExistException();
		}
		return user;
	}

	@Override
	public User createUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public User updateUser(int userId, User user, String auth) throws RestServerException {
		if (!authService.canEditUser(userId, auth)) {
			throw new TokenInvalidException();
		}
				
		User managed = userRepo.findById(userId).orElseThrow(UserDoesNotExistException::new);
		managed.setUsername(user.getUsername());
		managed.setEmail(user.getEmail());
		managed.setPassword(user.getPassword());
		
		if (authService.userIsAdmin(auth)) {
			managed.setEnabled(user.isEnabled());
			managed.setVerified(user.isVerified());
		} 
		
		userRepo.saveAndFlush(managed);
		return managed;
	}


}
