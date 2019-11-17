package com.event.qr.model;

import java.util.Date;

public class Questions {
	private int id;
	private int eventId;
	private String questionText;
	private String answer;
	private Date startTime;
	private Date endTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventid) {
		this.eventId = eventid;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questiontext) {
		this.questionText = questiontext;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date starttime) {
		this.startTime = starttime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endtime) {
		this.endTime = endtime;
	}

}