package com.skilldistillery.reviewit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.entities.User;
import com.skilldistillery.reviewit.repositories.UserRepository;
import com.skilldistillery.reviewit.util.UserDoesNotExistException;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;

	@Override
	public User getUserById(int userId) {
		return userRepo.findById(userId).orElse(null);
	}

	@Override
	public User createUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public User updateUserDetails(int userId, User user) throws UserDoesNotExistException {
		User managed = userRepo.findById(userId).orElseThrow(UserDoesNotExistException::new);
		managed.setUsername(user.getUsername());
		managed.setEmail(user.getEmail());
		managed.setPassword(user.getPassword());
		userRepo.saveAndFlush(managed);
		return managed;
	}

	@Override
	public User updateUserStatuses(int userId, User user) throws UserDoesNotExistException {
		User managed = userRepo.findById(userId).orElseThrow(UserDoesNotExistException::new);
		managed.setEnabled(user.isEnabled());
		managed.setVerified(user.isVerified());
		return managed;
	}

}
