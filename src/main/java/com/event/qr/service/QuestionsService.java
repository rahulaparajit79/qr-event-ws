package com.event.qr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.event.qr.util.ResponseObject;
import com.event.qr.util.ResponseList;
import com.event.qr.util.ResponseCodes;
import com.event.qr.model.Questions;
import com.event.qr.dao.QuestionsDAO;

import java.util.ArrayList;

@Service
public class QuestionsService {

	@Autowired
	QuestionsDAO questionsdao;

	public ResponseObject<Questions> saveQuestions(Questions questions) {

		ResponseObject<Questions> responseObject = new ResponseObject<>();
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

ResponseList<Questions> responseList = null;ArrayList<Questions> questionsList =null;

questionsList = questionsdao.getAllQuestionss();

if(questionsList.isEmpty()){

responseList = new ResponseList<>();
responseList.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
responseList.setResponseDesc("Failed to get all records");
responseList.setList(questionsList);
}else{

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

}