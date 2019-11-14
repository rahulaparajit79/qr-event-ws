package com.event.qr.security.service;

import java.io.Serializable;

import com.event.qr.model.User;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtAuthenticationResponse implements Serializable {

	private static final long serialVersionUID = 1250166508152483573L;

	private final String token;
	private  int errorCode;
	private  String errorDesc;
	private User user;

	public JwtAuthenticationResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}

	/**
	 * @param errorDesc the errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
