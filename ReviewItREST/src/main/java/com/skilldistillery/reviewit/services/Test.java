package com.skilldistillery.reviewit.services;

import org.mindrot.jbcrypt.BCrypt;

public class Test {
	public static void main(String[] args) {
		String testPass = "password555";
		String control = BCrypt.hashpw(testPass, BCrypt.gensalt());
		
		for (int i = 0; i < 10; i++) {
			
			System.out.println(BCrypt.checkpw(testPass, control));
		}
	}
}
