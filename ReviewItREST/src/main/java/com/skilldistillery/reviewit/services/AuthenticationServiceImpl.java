package com.skilldistillery.reviewit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.dtos.UserDTO;
import com.skilldistillery.reviewit.entities.User;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.repositories.UserRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public UserDTO register(UserDTO userDto) {
		User user = userDto.intoUser();
		user.setId(0);
		user.setEnabled(true);
		user.setRole("user");
		String password = encoder.encode(user.getPassword());
		user.setPassword(password);
		userDto = new UserDTO(userRepo.saveAndFlush(user));
		return userDto;
	}

	@Override
	public UserDTO getUserByUsername(String username) throws EntityDoesNotExistException {
		User user = userRepo
				.findByUsername(username)
				.orElseThrow(EntityDoesNotExistException::new);
		return new UserDTO(user);
	}

}
