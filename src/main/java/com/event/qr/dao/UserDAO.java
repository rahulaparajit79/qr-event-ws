package com.event.qr.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.event.qr.db.DBConnection;
import com.event.qr.model.User;

@Service
public class UserDAO {

	public boolean saveUser(User user) {

		Connection connection = null;
		CallableStatement cstatement = null;
		try {
			connection = DBConnection.getConnection();
			cstatement = connection.prepareCall("{ CALL User_AddDetails(?,?,?,?,?,?,?)}");

			cstatement.setInt("p_partyId", user.getPartyId());
			cstatement.setString("p_name", user.getName());
			cstatement.setString("p_mobileNo", user.getMobileNo());
			cstatement.setString("p_emailId", user.getEmailId());
			cstatement.setString("p_username", user.getUsername());
			cstatement.setString("p_password", user.getPassword());
			cstatement.setString("p_userImage", user.getUserImage());

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

	public ArrayList<User> getAllUsers() {

		Connection connection = DBConnection.getConnection();
		ResultSet resultSet = null;
		CallableStatement cstatement = null;

		User user = null;
		ArrayList<User> userList = new ArrayList<>();

		try {
			cstatement = connection.prepareCall("{CALL User_SelectAll()}");
			resultSet = cstatement.executeQuery();
			while (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getInt("id"));
				user.setPartyId(resultSet.getInt("partyId"));
				user.setName(resultSet.getString("name"));
				user.setMobileNo(resultSet.getString("mobileNo"));
				user.setEmailId(resultSet.getString("emailId"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setUserImage(resultSet.getString("userImage"));

				userList.add(user);

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

	public User getUserById(int id) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		User user = null;

		try {

			cstatement = connection.prepareCall("{CALL User_SelectById(?)}");
			cstatement.setInt("p_id", id);
			ResultSet resultSet = cstatement.executeQuery();

			if (resultSet.next()) {
				user = new User();

				user.setId(resultSet.getInt("id"));
				user.setPartyId(resultSet.getInt("partyId"));
				user.setName(resultSet.getString("name"));
				user.setMobileNo(resultSet.getString("mobileNo"));
				user.setEmailId(resultSet.getString("emailId"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setUserImage(resultSet.getString("userImage"));

				return user;
			} else {
				user = null;
				return user;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			user = new User();
			return user;

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

	public User getUserByUsername(String username) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		User user = null;

		try {

			cstatement = connection.prepareCall("{CALL User_SelectByUsername(?)}");
			cstatement.setString("p_username", username);
			ResultSet resultSet = cstatement.executeQuery();

			if (resultSet.next()) {
				user = new User();

				user.setId(resultSet.getInt("id"));
				user.setPartyId(resultSet.getInt("partyId"));
				user.setName(resultSet.getString("name"));
				user.setMobileNo(resultSet.getString("mobileNo"));
				user.setEmailId(resultSet.getString("emailId"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setUserImage(resultSet.getString("userImage"));

				return user;
			} else {
				user = null;
				return user;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			user = new User();
			return user;

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
	
	public User getUserByMobileNo(String mobileNo) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		User user = null;

		try {

			cstatement = connection.prepareCall("{CALL User_SelectByMobileNo(?)}");
			cstatement.setString("p_mobileNo", mobileNo);
			ResultSet resultSet = cstatement.executeQuery();

			if (resultSet.next()) {
				user = new User();

				user.setId(resultSet.getInt("id"));
				user.setPartyId(resultSet.getInt("partyId"));
				user.setName(resultSet.getString("name"));
				user.setMobileNo(resultSet.getString("mobileNo"));
				user.setEmailId(resultSet.getString("emailId"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setUserImage(resultSet.getString("userImage"));

				return user;
			} else {
				user = null;
				return user;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			user = new User();
			return user;

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

	public boolean updateUser(User user) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		try {

			cstatement = connection.prepareCall("{CALL User_UpdateById(?,?,?,?,?,?,?,?)}");
			cstatement.setInt("p_id", user.getId());
			cstatement.setInt("p_partyId", user.getPartyId());
			cstatement.setString("p_name", user.getName());
			cstatement.setString("p_mobileNo", user.getMobileNo());
			cstatement.setString("p_emailId", user.getEmailId());
			cstatement.setString("p_username", user.getUsername());
			cstatement.setString("p_password", user.getPassword());
			cstatement.setString("p_userImage", user.getUserImage());

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

	public boolean deleteUserById(int id) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		try {
			cstatement = connection.prepareCall("{CALL User_DeleteById(?)}");
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