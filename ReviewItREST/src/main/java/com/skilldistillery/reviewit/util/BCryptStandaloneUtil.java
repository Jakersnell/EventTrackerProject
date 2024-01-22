package com.skilldistillery.reviewit.util;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptStandaloneUtil {
	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		while (true) {
//			System.out.println("Enter a password to be encrypted\n>>>");
//			String input = sc.nextLine();
//			if (input.toLowerCase().equals(".exit")) {
//				break;
//			}
//			String encrypted = BCrypt.hashpw(input, BCrypt.gensalt());
//			System.out.println(encrypted);
//		}
//		System.out.println("Exiting.");
//		sc.close();
		String salt = BCrypt.gensalt();
		System.out.println(BCrypt.hashpw("password123", salt));
		System.out.println(salt);
	}
}