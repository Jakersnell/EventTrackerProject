package com.skilldistillery.reviewit.services;

import com.skilldistillery.reviewit.dtos.UserDTO;
import com.skilldistillery.reviewit.exceptions.DuplicateEntityException;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;

public interface AuthenticationService {

	UserDTO register(UserDTO userDto) throws DuplicateEntityException;

	UserDTO getUserByUsername(String username) throws EntityDoesNotExistException;

}
