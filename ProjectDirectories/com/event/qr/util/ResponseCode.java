package com.event.qr.util;

public enum ResponseCodes {

SUCCESS(200),FAILURE(500),UNAUTHORIZED(401),OK(200),
NOT_FOUND(404),ALREADY_USED(409),INACTIVE(403);
private final int responseCode;

public int getResponseCode() {
		return responseCode;
	}

private ResponseCodes(int responseCode) {
this.responseCode = responseCode;
}}
