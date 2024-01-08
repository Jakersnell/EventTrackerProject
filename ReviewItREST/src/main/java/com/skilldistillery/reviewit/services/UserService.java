package com.skilldistillery.reviewit.services;

import com.skilldistillery.reviewit.entities.User;
import com.skilldistillery.reviewit.exceptions.RestServerException;

public interface UserService {
	User getUserById(int userId, String auth) throws RestServerException;

	User createUser(User user);

	User updateUser(int userId, User user, String auth) throws RestServerException;

}
