package com.event.qr.dao;

import org.springframework.stereotype.Service;
import com.event.qr.db.DBConnection;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import com.event.qr.model.Images;

@Service
public class ImagesDAO {

	public boolean saveImages(Images images) {

		Connection connection = null;
		CallableStatement cstatement = null;
		try {
			connection = DBConnection.getConnection();
			cstatement = connection.prepareCall("{ CALL Images_AddDetails(?,?)}");

			cstatement.setString("p_base64Image", images.getBase64Image());
			cstatement.setString("p_caption", images.getCaption());

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

	public ArrayList<Images> getAllImagess() {

		Connection connection = DBConnection.getConnection();
		ResultSet resultSet = null;
		CallableStatement cstatement = null;

		Images images = null;
		ArrayList<Images> imagesList = new ArrayList<>();

		try {
			cstatement = connection.prepareCall("{CALL Images_SelectAll()}");
			resultSet = cstatement.executeQuery();
			while (resultSet.next()) {
				images = new Images();
				images.setImageId(resultSet.getInt("imageId"));
				images.setBase64Image(resultSet.getString("base64Image"));
				images.setCaption(resultSet.getString("caption"));

				imagesList.add(images);

			}
			return imagesList;

		} catch (Exception e) {
			e.printStackTrace();
			imagesList = null;
			return imagesList;

		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (cstatement != null) {
					cstatement.close();
				}
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public Images getImagesByImageId(int imageid) {
		Connection connection = DBConnection.getConnection();
		ResultSet resultSet = null;
		CallableStatement cstatement = null;

		Images images = null;

		try {

			cstatement = connection.prepareCall("{CALL Images_SelectByIMAGEID(?)}");
			cstatement.setInt("p_imageId", imageid);
			resultSet = cstatement.executeQuery();

			if (resultSet.next()) {
				images = new Images();

				images.setImageId(resultSet.getInt("imageId"));
				images.setBase64Image(resultSet.getString("base64Image"));
				images.setCaption(resultSet.getString("caption"));

				return images;
			} else {
				images = null;
				return images;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			images = new Images();
			return images;

		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (cstatement != null) {
					cstatement.close();
				}
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public boolean updateImages(Images images) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		try {

			cstatement = connection.prepareCall("{CALL Images_UpdateByIMAGEID(?,?,?)}");
			cstatement.setInt("p_imageId", images.getImageId());
			cstatement.setString("p_base64Image", images.getBase64Image());
			cstatement.setString("p_caption", images.getCaption());

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

	public boolean deleteImagesByImageId(int imageid) {
		Connection connection = DBConnection.getConnection();
		CallableStatement cstatement = null;

		try {
			cstatement = connection.prepareCall("{CALL Images_DeleteByIMAGEID(?)}");
			cstatement.setInt("p_imageId", imageid);
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