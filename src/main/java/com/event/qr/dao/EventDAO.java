package com.event.qr.dao;

import org.springframework.stereotype.Service;
import com.event.qr.db.DBConnection;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;

import com.event.qr.model.Event;
import com.event.qr.model.UserScore;
import com.event.qr.util.DateUtil;

@Service
public class EventDAO {

	public boolean saveEvent(Event event) {

		Connection connection = null;
		CallableStatement cstatement = null;
		try {
			connection = DBConnection.getConnection();
			cstatement = connection.prepareCall("{ CALL Event_AddDetails(?,?,?,?,?)}");

			cstatement.setString("p_eventName", event.getEventName());
			cstatement.setString("p_eventDesc", event.getEventDesc());
			cstatement.setString("p_eventStartTime", DateUtil.javaDateToSqlDateTimeString(event.getEventStartTime()));
			cstatement.setString("p_eventEndTime", DateUtil.javaDateToSqlDateTimeString(event.getEventEndTime()));
			cstatement.setString("p_eventStatus", "P");// always Pending for new event
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

	public ArrayList<Event> getAllEvents() {

		Connection connection = DBConnection.getConnection();
		ResultSet resultSet = null;
		CallableStatement cstatement = null;

		Event event = null;
		ArrayList<Event> eventList = new ArrayList<>();

		try {
			cstatement = connection.prepareCall("{CALL Event_SelectAll()}");
			resultSet = cstatement.executeQuery();
			while (resultSet.next()) {
				event = new Event();
				event.setId(resultSet.getInt("id"));
				event.setEventName(resultSet.getString("eventName"));
				event.setEventDesc(resultSet.getString("eventDesc"));
				event.setEventStartTime(DateUtil.sqlDateTimeToJavaDate(resultSet.getString("eventStartTime")));
				event.setEventEndTime(DateUtil.sqlDateTimeToJavaDate(resultSet.getString("eventEndTime")));
				event.setEventStatus(resultSet.getString("eventStatus"));

				eventList.add(event);

			}
			return eventList;

		} catch (Exception e) {
			e.printStackTrace();
			eventList = null;
			return eventList;

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

	public ArrayList<UserScore> getEventWinner(int eventId) {

		Connection connection = DBConnection.getConnection();
		ResultSet resultSet = null;
		CallableStatement cstatement = null;

		UserScore user = null;
		ArrayList<UserScore> userList = new ArrayList<>();

		try {
			cstatement = connection.prepareCall("{CALL Event_WinnerByEvent(?)}");
			cstatement.setInt("p_eventId",eventId);
			resultSet = cstatement.executeQuery();
			int rank = 1;
			while (resultSet.next()) {
				user = new UserScore();
				user.setUserId(resultSet.getInt("userId"));
				user.setMobileNo(resultSet.getString("mobileNo"));
				user.setPartyId(resultSet.getInt("partyId"));
				user.setScore(resultSet.getInt("score"));
				user.setUsername(resultSet.getString("name"));
				user.setRank(rank);

				userList.add(user);
				rank++;

			}
			return userList;

		} catch (Exception e) {
			e.printStackTrace();
			userList = null;
			return userList;

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
	
	public ArrayList<UserScore> getAllEventWinner() {

		Connection connection = DBConnection.getConnection();
		ResultSet resultSet = null;
		CallableStatement cstatement = null;

		UserScore user = null;
		ArrayList<UserScore> userList = new ArrayList<>();

		try {
			cstatement = connection.prepareCall("{CALL Event_WinnerAllEvent()}");
			
			int rank = 1;
			while (resultSet.next()) {
				user = new UserScore();
				user.setUserId(resultSet.getInt("userId"));
				user.setMobileNo(resultSet.getString("mobileNo"));
				user.setPartyId(resultSet.getInt("partyId"));
				user.setScore(resultSet.getInt("score"));
				user.setUsername(resultSet.getString("name"));
				user.setRank(rank);

				userList.add(user);
				rank++;

			}
			return userList;

		} catch (Exception e) {
			e.printStackTrace();
			userList = null;
			return userList;

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

	public ArrayList<Event> getAllPendingEvents() {

		Connection connection = DBConnection.getConnection();
		ResultSet resultSet = null;
		CallableStatement cstatement = null;

		Event event = null;
		ArrayList<Event> eventList = new ArrayList<>();

		try {
			cstatement = connection.prepareCall("{CALL Event_SelectAllPending()}");
			resultSet = cstatement.executeQuery();
			while (resultSet.next()) {
				event = new Event();
				event.setId(resultSet.getInt("id"));
				event.setEventName(resultSet.getString("eventName"));
				event.setEventDesc(resultSet.getString("eventDesc"));
				event.setEventStartTime(DateUtil.sqlDateTimeToJavaDate(resultSet.getString("eventStartTime")));
				event.setEventEndTime(DateUtil.sqlDateTimeToJavaDate(resultSet.getString("eventEndTime")));
				event.setEventStatus(resultSet.getString("eventStatus"));
				eventList.add(event);

			}
			return eventList;

		} catch (Exception e) {
			e.printStackTrace();
			eventList = null;
			return eventList;

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
	
	public ArrayList<Event> getAllClosedEvents() {

		Connection connection = DBConnection.getConnection();
		ResultSet resultSet = null;
		CallableStatement cstatement = null;

		Event event = null;
		ArrayList<Event> eventList = new ArrayList<>();

		try {
			cstatement = connection.prepareCall("{CALL Event_SelectAllClosed()}");
			resultSet = cstatement.executeQuery();
			while (resultSet.next()) {
				event = new Event();
				event.setId(resultSet.getInt("id"));
				event.setEventName(resultSet.getString("eventName"));
				event.setEventDesc(resultSet.getString("eventDesc"));
				event.setEventStartTime(DateUtil.sqlDateTimeToJavaDate(resultSet.getString("eventStartTime")));
				event.setEventEndTime(DateUtil.sqlDateTimeToJavaDate(resultSet.getString("eventEndTime")));
				event.setEventStatus(resultSet.getString("eventStatus"));
				eventList.add(event);

			}
			return eventList;

		} catch (Exception e) {
			e.printStackTrace();
			eventList = null;
			return eventList;

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

	public Event getEventById(int id) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		Event event = null;

		try {

			cstatement = connection.prepareCall("{CALL Event_SelectById(?)}");
			cstatement.setInt("p_id", id);
			ResultSet resultSet = cstatement.executeQuery();

			if (resultSet.next()) {
				event = new Event();

				event.setId(resultSet.getInt("id"));
				event.setEventName(resultSet.getString("eventName"));
				event.setEventDesc(resultSet.getString("eventDesc"));
				event.setEventStartTime(DateUtil.sqlDateTimeToJavaDate(resultSet.getString("eventStartTime")));
				event.setEventEndTime(DateUtil.sqlDateTimeToJavaDate(resultSet.getString("eventEndTime")));
				event.setEventStatus(resultSet.getString("eventStatus"));

				return event;
			} else {
				event = null;
				return event;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return event;

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

	public Event getActiveEvent() {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		Event event = null;

		try {

			cstatement = connection.prepareCall("{CALL Event_SelectActive()}");
			ResultSet resultSet = cstatement.executeQuery();

			if (resultSet.next()) {
				event = new Event();

				event.setId(resultSet.getInt("id"));
				event.setEventName(resultSet.getString("eventName"));
				event.setEventDesc(resultSet.getString("eventDesc"));
				event.setEventStartTime(DateUtil.sqlDateTimeToJavaDate(resultSet.getString("eventStartTime")));
				event.setEventEndTime(DateUtil.sqlDateTimeToJavaDate(resultSet.getString("eventEndTime")));
				event.setEventStatus(resultSet.getString("eventStatus"));

				return event;
			} else {
				event = null;
				return event;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return event;

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

	public Event getEventByEventName(String eventName) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;
		ResultSet resultSet = null;
		Event event = null;

		try {

			cstatement = connection.prepareCall("{CALL Event_SelectByEventName(?)}");
			cstatement.setString("p_eventName", eventName);
			resultSet = cstatement.executeQuery();

			if (resultSet.next()) {
				event = new Event();

				event.setId(resultSet.getInt("id"));
				event.setEventName(resultSet.getString("eventName"));
				event.setEventDesc(resultSet.getString("eventDesc"));
				event.setEventStartTime(DateUtil.sqlDateTimeToJavaDate(resultSet.getString("eventStartTime")));
				event.setEventEndTime(DateUtil.sqlDateTimeToJavaDate(resultSet.getString("eventEndTime")));
				event.setEventStatus(resultSet.getString("eventStatus"));

				return event;
			} else {
				event = null;
				return event;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return event;

		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (cstatement != null)
					cstatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}

	public int getEventCountBetweenTime(Date startTime, Date endTime) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;
		ResultSet resultSet = null;
		int count = 0;

		try {

			cstatement = connection.prepareCall("{CALL Event_GetCountBetweenTime(?,?)}");
			cstatement.setString("p_eventStartTime", DateUtil.javaDateToSqlDateTimeString(startTime));
			cstatement.setString("p_eventEndTime", DateUtil.javaDateToSqlDateTimeString(endTime));
			resultSet = cstatement.executeQuery();

			if (resultSet.next()) {

				count = resultSet.getInt(1);

				return count;
			} else {
				return count;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return -1;

		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (cstatement != null)
					cstatement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}

	public boolean updateEvent(Event event) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		try {

			cstatement = connection.prepareCall("{CALL Event_UpdateById(?,?,?,?,?)}");
			cstatement.setInt("p_id", event.getId());
			cstatement.setString("p_eventName", event.getEventName());
			cstatement.setString("p_eventDesc", event.getEventDesc());
			cstatement.setDate("p_eventStartTime", DateUtil.javaToSql(event.getEventStartTime()));
			cstatement.setDate("p_eventEndTime", DateUtil.javaToSql(event.getEventEndTime()));

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

	public boolean updateEventStatus(String status) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		try {

			cstatement = connection.prepareCall("{CALL Event_UpdateEventStatus(?)}");
			cstatement.setString("p_eventStatus", status);

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

	public boolean setEventStatusActive(int eventId) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		try {

			cstatement = connection.prepareCall("{CALL Event_UpdateEventStatusActiveById(?)}");
			cstatement.setInt("p_eventId", eventId);
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

	public boolean setEventStatusDisable(int eventId) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		try {

			cstatement = connection.prepareCall("{CALL Event_UpdateEventStatusDisableById(?)}");
			cstatement.setInt("p_eventId", eventId);
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

	public boolean deleteEventById(int id) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		try {
			cstatement = connection.prepareCall("{CALL Event_DeleteById(?)}");
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