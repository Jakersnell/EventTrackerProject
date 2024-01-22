package com.skilldistillery.reviewit.services;

import com.skilldistillery.reviewit.entities.AuthToken;
import com.skilldistillery.reviewit.entities.User;
import com.skilldistillery.reviewit.exceptions.BadRequestException;
import com.skilldistillery.reviewit.exceptions.RestServerException;

public interface AuthService {
	AuthToken getByToken(String token);

	boolean isActiveToken(String tokenString);

	AuthToken authenticate(String username, String password) throws RestServerException;

	boolean canEditUser(int userId, String tokenString) throws RestServerException;

	boolean userIsAdmin(String tokenString);

	User encryptPassword(String password, User user) throws BadRequestException;

}
