package com.event.qr.security;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.event.qr.security.service.JwtAuthenticationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = -8970718410437077606L;

	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		// This is invoked when user tries to access a secured REST resource without supplying any credentials
		// We should just send a 401 Unauthorized response because there is no 'login page' to redirect to

		//response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse(null);
		jwtResponse.setErrorCode(401);
		jwtResponse.setErrorDesc("Invalid username and password");
		response.setContentType("application/json");
		response.getWriter().append(new ObjectMapper().writeValueAsString(jwtResponse));


	}
}