package com.skilldistillery.reviewit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.reviewit.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	boolean existsByUsernameAndPassword(String username, String password);

	Optional<User> findByUsernameAndPassword(String username, String password);

	Optional<User> findByUsername(String username);
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
}
