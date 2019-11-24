package com.event.qr.model;

import java.util.Date;

public class Event {
	private int id;
	private String eventName;
	private String eventDesc;
	private Date eventStartTime;
	private Date eventEndTime;
	private String eventStatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventname) {
		this.eventName = eventname;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventdesc) {
		this.eventDesc = eventdesc;
	}

	public Date getEventStartTime() {
		return eventStartTime;
	}

	public void setEventStartTime(Date eventstarttime) {
		this.eventStartTime = eventstarttime;
	}

	public Date getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(Date eventendtime) {
		this.eventEndTime = eventendtime;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

}