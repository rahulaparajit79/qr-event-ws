package com.event.qr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthonticationProvider implements AuthenticationManager {

	@Autowired
	UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub

		String username = (String)authentication.getPrincipal();
		UserDetails ud=userDetailsService.loadUserByUsername(username);
		BCryptPasswordEncoder encPass=new BCryptPasswordEncoder();
		if(!encPass.matches((String)authentication.getCredentials(),ud.getPassword())){
			throw new AuthenticationException("Invalid Username and Password") {};
		}

		return authentication;
	}


}
