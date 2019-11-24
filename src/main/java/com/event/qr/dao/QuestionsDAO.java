package com.event.qr.dao;

import org.springframework.stereotype.Service;
import com.event.qr.db.DBConnection;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.event.qr.model.Questions;
import com.event.qr.util.DateUtil;

@Service
public class QuestionsDAO {

	public boolean saveQuestions(Questions questions) {

		Connection connection = null;
		CallableStatement cstatement = null;
		try {
			connection = DBConnection.getConnection();
			cstatement = connection.prepareCall("{ CALL Questions_AddDetails(?,?,?,?,?,?)}");

			cstatement.setInt("p_eventId", questions.getEventId());
			cstatement.setString("p_questionText", questions.getQuestionText());
			cstatement.setString("p_answer", questions.getAnswer());
			cstatement.setDate("p_startTime", DateUtil.javaToSql(questions.getStartTime()));
			cstatement.setDate("p_endTime", DateUtil.javaToSql(questions.getEndTime()));
			cstatement.setBoolean("p_isQuestionBonus", questions.isBonusQuestion());
			cstatement.executeQuery();

			return true;

		} catch (Exception e) {
			e.printStackTrace();

			return false;

		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Questions> getAllQuestionss() {

		Connection connection = DBConnection.getConnection();
		ResultSet resultSet = null;
		CallableStatement cstatement = null;

		Questions questions = null;
		ArrayList<Questions> questionsList = new ArrayList<>();

		try {
			cstatement = connection.prepareCall("{CALL Questions_SelectAll()}");
			resultSet = cstatement.executeQuery();
			while (resultSet.next()) {
				questions = new Questions();
				questions.setId(resultSet.getInt("id"));
				questions.setEventId(resultSet.getInt("eventId"));
				questions.setQuestionText(resultSet.getString("questionText"));
				questions.setAnswer(resultSet.getString("answer"));
				questions.setBonusQuestion(resultSet.getBoolean("isQuestionBonus"));
				

				questionsList.add(questions);

			}
			return questionsList;

		} catch (Exception e) {
			e.printStackTrace();
			questionsList = null;
			return questionsList;

		} finally {
			try {
				if (connection != null)
					connection.close();
				if (resultSet != null)
					resultSet.close();
				if (cstatement != null) {
					cstatement.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public Questions getQuestionsById(int id) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		Questions questions = null;

		try {

			cstatement = connection.prepareCall("{CALL Questions_SelectByID(?)}");
			cstatement.setInt("p_id", id);
			ResultSet resultSet = cstatement.executeQuery();

			if (resultSet.next()) {
				questions = new Questions();

				questions.setId(resultSet.getInt("id"));
				questions.setEventId(resultSet.getInt("eventId"));
				questions.setQuestionText(resultSet.getString("questionText"));
				questions.setAnswer(resultSet.getString("answer"));
				questions.setBonusQuestion(resultSet.getBoolean("isQuestionBonus"));

				return questions;
			} else {
				questions = null;
				return questions;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			questions = new Questions();
			return questions;

		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	
	public List<Questions> getQuestionsByEventId(int eventId) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;
List<Questions> qlist = new ArrayList<Questions>();
		Questions questions = null;

		try {

			cstatement = connection.prepareCall("{CALL Questions_SelectByEventID(?)}");
			cstatement.setInt("p_eventId", eventId);
			ResultSet resultSet = cstatement.executeQuery();

			while (resultSet.next()) {
				questions = new Questions();

				questions.setId(resultSet.getInt("id"));
				questions.setEventId(resultSet.getInt("eventId"));
				questions.setQuestionText(resultSet.getString("questionText"));
				questions.setAnswer(resultSet.getString("answer"));
				questions.setBonusQuestion(resultSet.getBoolean("isQuestionBonus"));

				qlist.add(questions);
			}
			return qlist;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return qlist;

		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}

	public boolean updateQuestions(Questions questions) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		try {

			cstatement = connection.prepareCall("{CALL Questions_UpdateByID(?,?,?,?,?,?)}");
			cstatement.setInt("p_id", questions.getId());
			cstatement.setInt("p_eventId", questions.getEventId());
			cstatement.setString("p_questionText", questions.getQuestionText());
			cstatement.setString("p_answer", questions.getAnswer());
			cstatement.setDate("p_startTime", DateUtil.javaToSql(questions.getStartTime()));
			cstatement.setDate("p_endTime", DateUtil.javaToSql(questions.getEndTime()));

			int result = cstatement.executeUpdate();

			if (result > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public boolean deleteQuestionsById(int id) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		try {
			cstatement = connection.prepareCall("{CALL Questions_DeleteByID(?)}");
			cstatement.setInt("p_id", id);
			boolean resultSet = cstatement.execute();

			if (resultSet) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}