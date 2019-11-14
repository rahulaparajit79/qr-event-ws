package com.event.qr.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		final String requestHeader = request.getHeader(this.tokenHeader);

		String username = null;
		String authToken = null;
		if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
			authToken = requestHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(authToken);
			} catch (IllegalArgumentException e) {
				logger.error("an error occured during getting username from token", e);
			} catch (ExpiredJwtException e) {
				logger.warn("the token is expired and not valid anymore", e);
			}catch(RuntimeException re){
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				return;
			}
		} else {
			logger.warn("couldn't find bearer string, will ignore the header");
		}

		logger.info("checking authentication for user " + username);
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// It is not compelling necessary to load the user details from the database. You could also store the information
			// in the token and read it from it. It's up to you ;)
			UserDetails userDetails=null;
			try{
				userDetails= this.userDetailsService.loadUserByUsername(username);
			}
			//This will generate Unauthorized 401 error if the user not found.
			catch(UsernameNotFoundException e){
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				return;
			}

			// For simple validation it is completely sufficient to just check the token integrity. You don't have to call
			// the database compellingly. Again it's up to you ;)
			if (userDetails!=null&&jwtTokenUtil.validateToken(authToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				logger.info("authenticated user " + username + ", setting security context");
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}else{
				////This will generate Unauthorized 401 error if the user token is invalid.
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
			}
		}

		chain.doFilter(request, response);
	}
}