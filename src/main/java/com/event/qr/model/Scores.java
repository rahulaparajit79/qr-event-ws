package com.event.qr.model;



public class Scores {
	private  int id;
private  int eventId;
private  int questionId;
private  int userId;
private  double score;

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

public int getQuestionId() {
	return questionId;
}

public void setQuestionId(int questionid) {
	this.questionId = questionid;
}

public int getUserId() {
	return userId;
}

public void setUserId(int userid) {
	this.userId = userid;
}

public double getScore() {
	return score;
}

public void setScore(double score) {
	this.score = score;
}

}