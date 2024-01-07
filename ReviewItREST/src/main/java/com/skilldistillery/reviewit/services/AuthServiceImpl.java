package com.skilldistillery.reviewit.services;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.entities.AuthToken;
import com.skilldistillery.reviewit.entities.User;
import com.skilldistillery.reviewit.repositories.AuthTokenRepository;
import com.skilldistillery.reviewit.repositories.UserRepository;
import com.skilldistillery.reviewit.util.RestServerException;
import com.skilldistillery.reviewit.util.TokenInvalidException;
import com.skilldistillery.reviewit.util.UserDoesNotExistException;

@Service
public class AuthServiceImpl implements AuthService {

	private static final int HOURS_BEFORE_EXPIRATION = 24;

	@Autowired
	private AuthTokenRepository authRepo;

	@Autowired
	private UserRepository userRepo;

	@Override
	public boolean isActiveToken(String tokenString) {
		AuthToken token = authRepo.findByToken(tokenString).orElse(null);
		return token != null && token.isEnabled() && token.getUser().isEnabled();
	}

	@Override
	public AuthToken getByToken(String tokenString) {
		return authRepo.findByToken(tokenString).orElse(null);
	}

	@Override
	public AuthToken authenticate(String username, String password) throws RestServerException {
		AuthToken token = null;
		User user = userRepo
				.findByUsernameAndPassword(username, password)
				.orElseThrow(UserDoesNotExistException::new);
		if (user.isEnabled()) {
			token = generateNewToken(username);
			token.setUser(user);
			authRepo.save(token);
		} else {
			throw new UserDoesNotExistException();
		}
		return token;
	}

	/// Generates a secure authentication token given username as a seed.
	/// The authentication token is then processed and made safe for URL.
	private AuthToken generateNewToken(String username) {
		String combinedData = username  + Instant.now().toEpochMilli(); // generate secure seed and create RNG
		SecureRandom random = new SecureRandom(combinedData.getBytes());
		byte[] bytes = new byte[32];
		random.nextBytes(bytes);
		String tokenString = Base64
				.getUrlEncoder()
				.withoutPadding() // remove padding as its not
				.encodeToString(bytes);
		LocalDateTime expiration = LocalDateTime
				.now()
				.plusHours(HOURS_BEFORE_EXPIRATION);
		return new AuthToken(tokenString, expiration);
	}

	@Override
	public boolean canEditUser(int userId, String tokenString) throws RestServerException {
		
		User user = userRepo
				.findById(userId)
				.orElseThrow(UserDoesNotExistException::new);

		AuthToken token = authRepo
				.findByToken(tokenString)
				.orElseThrow(TokenInvalidException::new);
		

		return (user.isAdmin()) || (user.isEnabled() && token.isEnabled() && token.getUser().equals(user));

	}

}
