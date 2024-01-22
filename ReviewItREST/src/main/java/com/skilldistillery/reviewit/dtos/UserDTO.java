package com.skilldistillery.reviewit.dtos;

import com.skilldistillery.reviewit.entities.User;

public class UserDTO {
	private String username;

	private String password;

	private String password2;

	private String email;

	private String role;

	public UserDTO() {
		super();
	}

	public UserDTO(String username, String password, String password2, String email) {
		super();
		this.username = username;
		this.password = password;
		this.password2 = password2;
		this.email = email;
	}

	public UserDTO(User user) {
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.role = user.getRole();
	}

	public User intoUser() {
		User user = new User();

		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setRole(role);

		return user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", password=" + password + "]";
	}

}
