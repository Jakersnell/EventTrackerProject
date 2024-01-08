package com.skilldistillery.reviewit.services;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.entities.AuthToken;
import com.skilldistillery.reviewit.entities.User;
import com.skilldistillery.reviewit.exceptions.BadRequestException;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.exceptions.RestServerException;
import com.skilldistillery.reviewit.exceptions.TokenInvalidException;
import com.skilldistillery.reviewit.repositories.AuthTokenRepository;
import com.skilldistillery.reviewit.repositories.UserRepository;

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
		if (token == null) {
			return false;
		}
		boolean isActive;
		if (LocalDateTime.now().isAfter(token.getExpiresOn())) {
			token.setEnabled(false);
			isActive = false;
		} else {
			isActive = token != null && token.isEnabled() && token.getUser().isEnabled();
		}
		return isActive;
	}

	@Override
	public AuthToken getByToken(String tokenString) {
		AuthToken token = authRepo.findByToken(tokenString).orElse(null);
		if (token != null && !isActiveToken(tokenString)) {
			token = null;
		}
		return token;
	}

	@Override
	public AuthToken authenticate(String username, String password) throws RestServerException {
		if (username == null || password == null) {
			throw new BadRequestException();
		}
		AuthToken token = null;
		User user = userRepo.findByUsernameAndPassword(username, password).orElseThrow(TokenInvalidException::new);
		if (user.isEnabled()) {
			token = generateNewToken(username);
			token.setUser(user);
			authRepo.save(token);
		} else {
			throw new TokenInvalidException();
		}
		return token;
	}

	private AuthToken generateNewToken(String username) {
		String combinedData = username + Instant.now().toEpochMilli(); // generate secure seed and create RNG
		SecureRandom random = new SecureRandom(combinedData.getBytes());
		byte[] bytes = new byte[32];
		random.nextBytes(bytes);
		String tokenString = Base64.getUrlEncoder().withoutPadding() // remove padding as its not
				.encodeToString(bytes);
		LocalDateTime expiration = LocalDateTime.now().plusHours(HOURS_BEFORE_EXPIRATION);
		return new AuthToken(tokenString, expiration);
	}

	@Override
	public boolean canEditUser(int userId, String tokenString) throws RestServerException {
		if (tokenString == null || !isActiveToken(tokenString)) {
			throw new TokenInvalidException();
		}

		User user = userRepo.findById(userId).orElseThrow(EntityDoesNotExistException::new);

		AuthToken token = authRepo.findByToken(tokenString).orElseThrow(TokenInvalidException::new);

		return (user.isAdmin()) || (user.isEnabled() && token.isEnabled() && token.getUser().equals(user));

	}

	@Override
	public boolean userIsAdmin(String auth) {
		boolean isValid = false;
		AuthToken token = null;

		if (auth != null && isActiveToken(auth)) {
			token = authRepo.findByToken(auth).orElse(null);
		}

		if (token != null) {
			isValid = token.getUser().isAdmin();
		}

		return isValid;
	}

}
