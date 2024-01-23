package com.skilldistillery.reviewit.services;

import com.skilldistillery.reviewit.dtos.UserDTO;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;

public interface AuthenticationService {
	
	UserDTO register(UserDTO userDto);

	UserDTO getUserByUsername(String username) throws EntityDoesNotExistException ;
	
}
