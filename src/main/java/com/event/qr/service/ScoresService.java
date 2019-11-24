package com.event.qr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.event.qr.util.ResponseObject;
import com.event.qr.util.ResponseCodes;
import com.event.qr.util.ResponseList;
import com.event.qr.model.Scores;
import com.event.qr.dao.ScoresDAO;

import java.util.ArrayList;

@Service
public class ScoresService {

	@Autowired
	ScoresDAO scoresdao;

	public ResponseObject<Scores> saveScores(Scores scores) {

		ResponseObject<Scores> responseObject = new ResponseObject<>();
		if (scoresdao.saveScores(scores)) {

			responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseObject.setResponseDesc("Record has been saved successfully");
			responseObject.setResponseData(scores);

			return responseObject;
		} else {

			scores = null;

			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc("Failed to save record");
			responseObject.setResponseData(scores);

			return responseObject;
		}

	}

	public ResponseList<Scores> getAllScoress() {

		ResponseList<Scores> responseList = null;
		ArrayList<Scores> scoresList = null;

		scoresList = scoresdao.getAllScoress();

		if (scoresList.isEmpty()) {

			responseList = new ResponseList<>();
			responseList.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseList.setResponseDesc("Failed to get all records");
			responseList.setList(scoresList);
		} else {

			responseList = new ResponseList<>();
			responseList.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseList.setResponseDesc("All records fetched successfully");
			responseList.setList(scoresList);
		}
		return responseList;
	}

	public ResponseObject<Scores> getScoresById(int id) {

		ResponseObject<Scores> responseObject = new ResponseObject<>();
		Scores scores = null;

		scores = scoresdao.getScoresById(id);

		if (scores != null) {

			responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseObject.setResponseDesc("Record found");
			responseObject.setResponseData(scores);

			return responseObject;
		} else {

			scores = null;

			responseObject.setResponseCode(ResponseCodes.NOT_FOUND.getResponseCode());
			responseObject.setResponseDesc("Record not found ");
			responseObject.setResponseData(scores);

			return responseObject;
		}

	}

	public ResponseObject<Scores> updateScores(Scores scores) {

		ResponseObject<Scores> responseObject = new ResponseObject<>();
		if (scoresdao.updateScores(scores)) {

			responseObject.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			responseObject.setResponseDesc("Record updated successfully");
			responseObject.setResponseData(scores);

			return responseObject;

		} else {

			responseObject.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			responseObject.setResponseDesc("Failed to update record");
			responseObject.setResponseData(scores);

			return responseObject;

		}
	}

	public ResponseObject<Scores> deleteScoresById(int id) {

		ResponseObject<Scores> responseObject = new ResponseObject<>();

		if (scoresdao.deleteScoresById(id)) {

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