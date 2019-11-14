package com.event.qr.dao;

import org.springframework.stereotype.Service;
import com.event.qr.db.DBConnection;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import com.event.qr.model.Event;
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

			cstatement.setDate("p_eventStartTime", DateUtil.javaToSql(event.getEventStartTime()));
			cstatement.setDate("p_eventEndTime", DateUtil.javaToSql(event.getEventEndTime()));

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

				event.setEventStartTime(resultSet.getDate("eventStartTime"));
				event.setEventEndTime(resultSet.getDate("eventEndTime"));

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

				event.setEventStartTime(resultSet.getDate("eventStartTime"));
				event.setEventEndTime(resultSet.getDate("eventEndTime"));

				return event;
			} else {
				event = null;
				return event;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			event = new Event();
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

	public boolean updateEvent(Event event) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		try {

			cstatement = connection.prepareCall("{CALL Event_UpdateById(?,?,?,?,?,?)}");
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