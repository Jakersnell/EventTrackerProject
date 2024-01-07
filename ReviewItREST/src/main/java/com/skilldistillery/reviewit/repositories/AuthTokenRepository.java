package com.skilldistillery.reviewit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.reviewit.entities.AuthToken;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Integer> {
	Optional<AuthToken> findByToken(String token);
}
