package com.event.qr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.event.qr.util.ResponseObject;
import com.event.qr.util.ResponseList;
import com.event.qr.util.Constants;
import com.event.qr.util.ResponseCodes;
import com.event.qr.model.Images;
import com.event.qr.dao.ImagesDAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

@Service
public class ImagesService {

	@Autowired
	ImagesDAO imagesdao;

	public ResponseObject<Images> saveImages(Images images) {

		ResponseObject<Images> responseObject = new ResponseObject<>();
		String status = validateImageData(images);

		if (!status.equals("OK")) {
			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc(status);
		}

		String encodedImg = images.getBase64Image();
		File imgFile = null;

		try {
			long filename = new Date().getTime();
			imgFile = new File(Constants.IMG_GALLARY_PATH + filename + ".txt");
			Files.write(imgFile.toPath(), encodedImg.getBytes());
			
			responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseObject.setResponseDesc("Record has been saved successfully");
			responseObject.setResponseData(images);

			return responseObject;

		} catch (IOException e) {
			e.printStackTrace();

			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc(e.getMessage());
			return responseObject;
		} catch (Exception e) {
			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc(e.getMessage());
			return responseObject;
		}

		/*
		 * if (imagesdao.saveImages(images)) {
		 * 
		 * responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
		 * responseObject.setResponseDesc("Record has been saved successfully");
		 * responseObject.setResponseData(images);
		 * 
		 * return responseObject; } else {
		 * 
		 * images = null;
		 * 
		 * responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
		 * responseObject.setResponseDesc("Failed to save record");
		 * responseObject.setResponseData(images);
		 * 
		 * return responseObject; }
		 */

	}
	
	public ResponseList<Images> getAllImagess() {

		ResponseList<Images> responseList =  new ResponseList<>();;
		ArrayList<Images> imagesList = new ArrayList<>();
		
		File[] imgFileList = new File(Constants.IMG_GALLARY_PATH).listFiles();
		
		if(imgFileList==null || imgFileList.length ==0) {
			responseList.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseList.setResponseDesc("No file found.");
			responseList.setList(imagesList);
			return responseList;
		}
		
		for(File file: imgFileList) {
			try {
				String encodedImg = new String(Files.readAllBytes(file.toPath()));
				Images images = new Images();
				images.setBase64Image(encodedImg);
				imagesList.add(images);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}

		if (imagesList.isEmpty()) {

			responseList.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseList.setResponseDesc("Failed to get all records");
			responseList.setList(imagesList);
		} else {

			responseList = new ResponseList<>();
			responseList.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseList.setResponseDesc("All records fetched successfully");
			responseList.setList(imagesList);
		}
		return responseList;
	}

	/*
	 * public ResponseList<Images> getAllImagess() {
	 * 
	 * ResponseList<Images> responseList = null; ArrayList<Images> imagesList =
	 * null;
	 * 
	 * imagesList = imagesdao.getAllImagess();
	 * 
	 * if (imagesList.isEmpty()) {
	 * 
	 * responseList = new ResponseList<>();
	 * responseList.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
	 * responseList.setResponseDesc("Failed to get all records");
	 * responseList.setList(imagesList); } else {
	 * 
	 * responseList = new ResponseList<>();
	 * responseList.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
	 * responseList.setResponseDesc("All records fetched successfully");
	 * responseList.setList(imagesList); } return responseList; }
	 */

	public ResponseObject<Images> getImagesByImageId(int imageid) {

		ResponseObject<Images> responseObject = new ResponseObject<>();
		Images images = null;

		images = imagesdao.getImagesByImageId(imageid);

		if (images != null) {

			responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseObject.setResponseDesc("Record found");
			responseObject.setResponseData(images);

			return responseObject;
		} else {

			images = null;

			responseObject.setResponseCode(ResponseCodes.NOT_FOUND.getResponseCode());
			responseObject.setResponseDesc("Record not found ");
			responseObject.setResponseData(images);

			return responseObject;
		}

	}

	public ResponseObject<Images> updateImages(Images images) {

		ResponseObject<Images> responseObject = new ResponseObject<>();
		if (imagesdao.updateImages(images)) {

			responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseObject.setResponseDesc("Record updated successfully");
			responseObject.setResponseData(images);

			return responseObject;

		} else {

			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc("Failed to update record");
			responseObject.setResponseData(images);

			return responseObject;

		}
	}

	public ResponseObject<Images> deleteImagesByImageId(int imageid) {

		ResponseObject<Images> responseObject = new ResponseObject<>();

		if (imagesdao.deleteImagesByImageId(imageid)) {

			responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseObject.setResponseDesc("Record deleted successfully");

			return responseObject;

		} else {

			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc("Failed to delete record");

			return responseObject;

		}

	}

	private String validateImageData(Images images) {
		String status = "OK";

		if (images == null) {
			return "Please provide image";
		}
		if (images.getBase64Image() == null || images.getBase64Image().trim().isEmpty()) {
			return "Please provide image";
		}
		if (images.getCaption() == null || images.getCaption().trim().isEmpty()) {
			return "Please provide image caption";
		}

		return status;

	}

}