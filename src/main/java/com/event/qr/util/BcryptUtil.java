package com.event.qr.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptUtil {
	
	public static String encrypt(String plainText) {
		return new BCryptPasswordEncoder().encode(plainText);
	}

}
