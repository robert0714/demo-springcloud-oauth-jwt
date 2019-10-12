package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

	public static void main(String[] args) {
		String encode01 = new BCryptPasswordEncoder().encode("secret");
		System.out.println(encode01);
		if(new BCryptPasswordEncoder().matches("secret", "$2a$10$c85hYXPx4niZCCkmxeqXHOriQvvaWBSd9SVpYoq2ZAbs0uUa1ESL.")) {
			System.out.println("ok1");
		}
		String encode02 = new BCryptPasswordEncoder().encode("123456");
		                                                //$2a$10$c85hYXPx4niZCCkmxeqXHOriQvvaWBSd9SVpYoq2ZAbs0uUa1ESL.
		if(new BCryptPasswordEncoder().matches("123456", "$2a$10$c85hYXPx4niZCCkmxeqXHOriQvvaWBSd9SVpYoq2ZAbs0uUa1ESL.")) {
			System.out.println("ok2");
		}
	}

}
