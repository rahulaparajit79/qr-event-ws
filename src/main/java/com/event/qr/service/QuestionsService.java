package com.event.qr.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.event.qr.dao.EventDAO;
import com.event.qr.dao.QuestionsDAO;
import com.event.qr.dao.ScoresDAO;
import com.event.qr.model.AnswerQr;
import com.event.qr.model.Event;
import com.event.qr.model.Questions;
import com.event.qr.model.Scores;
import com.event.qr.util.ResponseCodes;
import com.event.qr.util.ResponseList;
import com.event.qr.util.ResponseObject;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

@Service
public class QuestionsService {

	@Autowired
	QuestionsDAO questionsdao;
	@Autowired
	ScoresService scoresService;
	@Autowired
	ScoresDAO scoresDAO;
	@Autowired
	EventDAO eventDAO;

	public ResponseObject<Questions> saveQuestions(Questions questions) {
		ResponseObject<Questions> responseObject = new ResponseObject<>();

		String status = validateQuestion(questions);

		if (!status.equals("OK")) {
			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc(status);
			return responseObject;
		}

		String qrValue = decodeQRCode(questions.getAnswer());

		if (qrValue.equals("There is no QR code in the image")) {
			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc("There is no QR code in the image");
			return responseObject;
		} else if (qrValue.equals("Error")) {
			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc("Error reading QR code.");
			return responseObject;
		}

		questions.setAnswer(qrValue);
		if (questionsdao.saveQuestions(questions)) {

			responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseObject.setResponseDesc("Record has been saved successfully");
			responseObject.setResponseData(questions);

			return responseObject;
		} else {

			questions = null;

			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc("Failed to save record");
			responseObject.setResponseData(questions);

			return responseObject;
		}

	}

	public ResponseList<Questions> getAllQuestionss() {

		ResponseList<Questions> responseList = null;
		ArrayList<Questions> questionsList = null;

		questionsList = questionsdao.getAllQuestionss();

		if (questionsList.isEmpty()) {

			responseList = new ResponseList<>();
			responseList.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseList.setResponseDesc("Failed to get all records");
			responseList.setList(questionsList);
		} else {

			responseList = new ResponseList<>();
			responseList.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseList.setResponseDesc("All records fetched successfully");
			responseList.setList(questionsList);
		}
		return responseList;
	}

	public ResponseObject<Questions> getQuestionsById(int id) {

		ResponseObject<Questions> responseObject = new ResponseObject<>();
		Questions questions = null;

		questions = questionsdao.getQuestionsById(id);

		if (questions != null) {

			responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseObject.setResponseDesc("Record found");
			responseObject.setResponseData(questions);

			return responseObject;
		} else {

			questions = null;

			responseObject.setResponseCode(ResponseCodes.NOT_FOUND.getResponseCode());
			responseObject.setResponseDesc("Record not found ");
			responseObject.setResponseData(questions);

			return responseObject;
		}

	}

	public ResponseList<Questions> getQuestionsByEventId(int eventId) {

		ResponseList<Questions> responseObject = new ResponseList<>();
		List<Questions> questions = null;

		if (eventId == 0) {
			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc("Provide eventID");
			return responseObject;
		}

		questions = questionsdao.getQuestionsByEventId(eventId);

		if (questions != null) {

			responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseObject.setResponseDesc("Record found");
			responseObject.setList(questions);

			return responseObject;
		} else {

			questions = null;

			responseObject.setResponseCode(ResponseCodes.NOT_FOUND.getResponseCode());
			responseObject.setResponseDesc("Record not found ");
			responseObject.setList(questions);

			return responseObject;
		}

	}

	public ResponseObject<Questions> updateQuestions(Questions questions) {

		ResponseObject<Questions> responseObject = new ResponseObject<>();
		if (questionsdao.updateQuestions(questions)) {

			responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseObject.setResponseDesc("Record updated successfully");
			responseObject.setResponseData(questions);

			return responseObject;

		} else {

			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc("Failed to update record");
			responseObject.setResponseData(questions);

			return responseObject;

		}
	}

	public ResponseObject<Questions> deleteQuestionsById(int id) {

		ResponseObject<Questions> responseObject = new ResponseObject<>();

		if (questionsdao.deleteQuestionsById(id)) {

			responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseObject.setResponseDesc("Record deleted successfully");

			return responseObject;

		} else {

			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc("Failed to delete record");

			return responseObject;

		}

	}

	public ResponseObject<String> answer(AnswerQr answerQr) {

		ResponseObject<String> response = new ResponseObject<>();

		String status = validateAnswer(answerQr);
		if (!status.equals("OK")) {
			response.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			response.setResponseDesc(status);
			return response;
		}

		int count =-1;
		count = scoresDAO.getUserCountForEventQuestion(answerQr.getEventId(), answerQr.getQuestionId(),
				answerQr.getUserId());

		if (count > 0) {
			response.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			response.setResponseDesc("Sorry! already answered.");
			return response;
		}else if(count ==-1) {
			response.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			response.setResponseDesc("Error processing answer. Please try again.");
			return response;
		}

		Questions question = questionsdao.getQuestionsById(answerQr.getQuestionId());

		if (question == null) {
			response.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			response.setResponseDesc("Error processing answer.Invalid question.");
			return response;
		}

		String encodedImg = answerQr.getQrCode();

		try {
			String result = decodeQRCode(encodedImg);
			if (!result.equals(question.getAnswer())) {
				response.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
				response.setResponseDesc("Incorrect answer.");
				return response;
			}

			if (question.isBonusQuestion()) {

				List<Scores> scoreList = scoresDAO.getScoresByQuestionIdEventId(answerQr.getQuestionId(),
						answerQr.getEventId());

				if (scoreList == null) {
					response.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
					response.setResponseDesc("Error processing answer. Invalid event.");
					return response;
				}
				int points = 10;
				/* First user answer for the particcular event & question */
				if (scoreList.size() == 0) {
					points += 50;
				} else if (scoreList.size() == 1) {
					points += 40;
				} else if (scoreList.size() == 2) {
					points += 30;
				} else if (scoreList.size() == 3) {
					points += 20;
				} else if (scoreList.size() == 4) {
					points += 10;
				}

				boolean rslt = scoresDAO.saveScores(answerQr.getEventId(), answerQr.getQuestionId(),
						answerQr.getUserId(), points);
				if (!rslt) {
					response.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
					response.setResponseDesc("Unable to process, please try again.");
					return response;
				} else {
					response.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
					response.setResponseData("Hurrayyy..!, +" + points + " points.");

					return response;
				}

			} else {

				boolean rslt = scoresDAO.saveScores(answerQr.getEventId(), answerQr.getQuestionId(),
						answerQr.getUserId(), 10);
				if (!rslt) {
					response.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
					response.setResponseDesc("Unable to process, please try again.");
					return response;
				} else {
					response.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
					response.setResponseData("Correct answer, +10 points.");
					response.setResponseDesc(result);
					return response;
				}

			}

		} catch (Exception e) {
			response.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			response.setResponseDesc(e.getMessage());
			return response;
		}

	}

	private static String decodeQRCode(String encodedImg) {

		File imgFile = null;
		try {
			imgFile = File.createTempFile("qrImage", ".png");
			Path destinationFile = imgFile.toPath();
			byte[] decodedImg = DatatypeConverter.parseBase64Binary(encodedImg);
			Files.write(destinationFile, decodedImg);
			BufferedImage bufferedImage = ImageIO.read(imgFile);
			LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			Result result = new MultiFormatReader().decode(bitmap);
			imgFile.delete();

			return result.getText();
		} catch (NotFoundException e) {
			System.out.println("There is no QR code in the image");
			return "There is no QR code in the image";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error";
		} catch (Exception e) {
			e.printStackTrace();

			return "Error";
		}
	}

	private String validateAnswer(AnswerQr answerQr) {
		String status = "OK";
		if (answerQr == null) {
			return "No answer";
		}
		if (answerQr.getUserId() == 0) {
			return "Please provide userId";
		}
		if (answerQr.getEventId() == 0) {
			return "Please provide eventId";
		}
		if (answerQr.getQuestionId() == 0) {
			return "Please provide question Id";
		}
		if (answerQr.getQrCode() == null || answerQr.getQrCode().trim().isEmpty()) {
			return "Answer QR code is blank.";
		}

		return status;
	}

	private String validateQuestion(Questions questions) {
		String status = "OK";

		if (questions == null) {
			return "Please provide question.";
		}

		if (questions.getEventId() == 0) {
			return "Please provide event id.";

		}
		if (questions.getQuestionText() == null || questions.getQuestionText().trim().isEmpty()) {
			return "Please provide question.";
		}
		if (questions.getAnswer() == null || questions.getAnswer().trim().isEmpty()) {
			return "Please provide answer.";
		}
		Event event = eventDAO.getActiveEvent();

		if(event !=null) {
			return "Another event is already active, please stop that event first.";
		}

		return status;
	}

}