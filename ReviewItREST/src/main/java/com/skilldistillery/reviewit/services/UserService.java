package com.skilldistillery.reviewit.services;

import com.skilldistillery.reviewit.entities.User;
import com.skilldistillery.reviewit.util.UserDoesNotExistException;

public interface UserService {
	User getUserById(int userId);

	User createUser(User user);

	User updateUserDetails(int userId, User user) throws UserDoesNotExistException;

	User updateUserStatuses(int userId, User user) throws UserDoesNotExistException;
	
}
