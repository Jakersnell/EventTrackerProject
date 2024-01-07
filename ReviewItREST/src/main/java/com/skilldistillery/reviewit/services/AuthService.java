package com.skilldistillery.reviewit.services;

import com.skilldistillery.reviewit.entities.AuthToken;
import com.skilldistillery.reviewit.util.RestServerException;

public interface AuthService {
	AuthToken getByToken(String token);

	boolean isActiveToken(String tokenString);

	AuthToken authenticate(String username, String password)  throws RestServerException ;

	boolean canEditUser(int userId, String tokenString) throws RestServerException;
	
}
