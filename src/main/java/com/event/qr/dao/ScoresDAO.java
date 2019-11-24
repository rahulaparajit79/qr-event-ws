package com.event.qr.dao;

import org.springframework.stereotype.Service;
import com.event.qr.db.DBConnection;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import com.event.qr.model.Scores;

@Service
public class ScoresDAO {

	public boolean saveScores(Scores scores) {

		Connection connection = null;
		CallableStatement cstatement = null;
		try {
			connection = DBConnection.getConnection();
			cstatement = connection.prepareCall("{ CALL Scores_AddDetails(?,?,?,?)}");

			cstatement.setInt("p_eventId", scores.getEventId());
			cstatement.setInt("p_questionId", scores.getQuestionId());
			cstatement.setInt("p_userId", scores.getUserId());
			cstatement.setDouble("p_score", scores.getScore());

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

	public boolean saveScores(int p_eventId, int p_questionId, int userId, int points) {

		Connection connection = null;
		CallableStatement cstatement = null;
		try {
			connection = DBConnection.getConnection();
			cstatement = connection.prepareCall("{ CALL Scores_AddDetails(?,?,?,?)}");

			cstatement.setInt("p_eventId", p_eventId);
			cstatement.setInt("p_questionId", p_questionId);
			cstatement.setInt("p_userId", userId);
			cstatement.setDouble("p_score", points);

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

	public ArrayList<Scores> getAllScoress() {

		Connection connection = DBConnection.getConnection();
		ResultSet resultSet = null;
		CallableStatement cstatement = null;

		Scores scores = null;
		ArrayList<Scores> scoresList = new ArrayList<>();

		try {
			cstatement = connection.prepareCall("{CALL Scores_SelectAll()}");
			resultSet = cstatement.executeQuery();
			while (resultSet.next()) {
				scores = new Scores();
				scores.setId(resultSet.getInt("id"));
				scores.setEventId(resultSet.getInt("eventId"));
				scores.setQuestionId(resultSet.getInt("questionId"));
				scores.setUserId(resultSet.getInt("userId"));
				scores.setScore(resultSet.getDouble("score"));

				scoresList.add(scores);

			}
			return scoresList;

		} catch (Exception e) {
			e.printStackTrace();
			scoresList = null;
			return scoresList;

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

	public ArrayList<Scores> getScoresByQuestionIdEventId(int questionId, int eventId) {

		Connection connection = DBConnection.getConnection();
		ResultSet resultSet = null;
		CallableStatement cstatement = null;

		Scores scores = null;
		ArrayList<Scores> scoresList = null;

		try {
			cstatement = connection.prepareCall("{CALL Scores_SelectAllByQuestionIdEventId(?,?)}");
			cstatement.setInt("p_questionId", questionId);
			cstatement.setInt("p_eventId", eventId);
			resultSet = cstatement.executeQuery();
			scoresList = new ArrayList<>();
			while (resultSet.next()) {
				scores = new Scores();
				scores.setId(resultSet.getInt("id"));
				scores.setEventId(resultSet.getInt("eventId"));
				scores.setQuestionId(resultSet.getInt("questionId"));
				scores.setUserId(resultSet.getInt("userId"));
				scores.setScore(resultSet.getDouble("score"));

				scoresList.add(scores);

			}
			return scoresList;

		} catch (Exception e) {
			e.printStackTrace();
			scoresList = null;
			return scoresList;

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

	public Scores getScoresById(int id) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		Scores scores = null;

		try {

			cstatement = connection.prepareCall("{CALL Scores_SelectByID(?)}");
			cstatement.setInt("p_id", id);
			ResultSet resultSet = cstatement.executeQuery();

			if (resultSet.next()) {
				scores = new Scores();

				scores.setId(resultSet.getInt("id"));
				scores.setEventId(resultSet.getInt("eventId"));
				scores.setQuestionId(resultSet.getInt("questionId"));
				scores.setUserId(resultSet.getInt("userId"));
				scores.setScore(resultSet.getDouble("score"));

				return scores;
			} else {
				scores = null;
				return scores;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			scores = new Scores();
			return scores;

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

	public boolean updateScores(Scores scores) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		try {

			cstatement = connection.prepareCall("{CALL Scores_UpdateByID(?,?,?,?,?)}");
			cstatement.setInt("p_id", scores.getId());
			cstatement.setInt("p_eventId", scores.getEventId());
			cstatement.setInt("p_questionId", scores.getQuestionId());
			cstatement.setInt("p_userId", scores.getUserId());
			cstatement.setDouble("p_score", scores.getScore());

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

	public boolean deleteScoresById(int id) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		try {
			cstatement = connection.prepareCall("{CALL Scores_DeleteByID(?)}");
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

	public boolean addPointsToUser(int p_eventId, int p_questionId, int userId, int points) {

		Connection connection = null;
		CallableStatement cstatement = null;
		try {
			connection = DBConnection.getConnection();
			cstatement = connection.prepareCall("{ CALL Scores_AddPointsToUser(?,?,?,?)}");

			cstatement.setInt("p_eventId", p_eventId);
			cstatement.setInt("p_questionId", p_questionId);
			cstatement.setInt("p_userId", userId);
			cstatement.setDouble("p_points", points);

			cstatement.executeQuery();

			return true;

		} catch (Exception e) {
			e.printStackTrace();

			return false;

		} finally {
			try {
				if (cstatement != null)
					cstatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int getUserCountForEventQuestion(int p_eventId, int p_questionId, int userId) {

		Connection connection = null;
		CallableStatement cstatement = null;
		ResultSet rs = null;
		try {
			connection = DBConnection.getConnection();
			cstatement = connection.prepareCall("{ CALL Scores_CountUserByEventQuestion(?,?,?)}");

			cstatement.setInt("p_eventId", p_eventId);
			cstatement.setInt("p_questionId", p_questionId);
			cstatement.setInt("p_userId", userId);

			rs = cstatement.executeQuery();

			if (rs != null && rs.next()) {
				return rs.getInt("count");
			}
			return -1;

		} catch (Exception e) {
			e.printStackTrace();

			return -1;

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (cstatement != null)
					cstatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}