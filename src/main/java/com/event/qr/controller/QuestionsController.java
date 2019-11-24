package com.event.qr.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.event.qr.util.ResponseObject;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.event.qr.util.ResponseCodes;
import com.event.qr.util.ResponseList;

import com.event.qr.service.QuestionsService;
import com.event.qr.model.AnswerQr;
import com.event.qr.model.Base64QR;
import com.event.qr.model.Questions;

import org.springframework.beans.factory.annotation.Autowired;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/questions")
public class QuestionsController {

	@Autowired
	QuestionsService questionsservice;

	@PostMapping("/add")
	public ResponseObject<Questions> saveQuestions(@RequestBody Questions questions) {

		return questionsservice.saveQuestions(questions);
	}

	@GetMapping("/getbyid")
	public ResponseObject<Questions> getQuestionsById(@RequestParam(value = "id", required = true) int id) {

		return questionsservice.getQuestionsById(id);

	}
	
	@GetMapping("/getbyeventid")
	public ResponseList<Questions> getQuestionsByEventId(@RequestParam(value = "eventId", required = true) int eventId) {

		return questionsservice.getQuestionsByEventId(eventId);

	}

	@GetMapping("/listall")
	public ResponseList<Questions> getQuestionsListAll() {

		return questionsservice.getAllQuestionss();
	}

	@PutMapping("/update")
	public ResponseObject<Questions> updateQuestions(@RequestBody Questions questions) {

		return questionsservice.updateQuestions(questions);
	}

	@DeleteMapping("/delete")
	public ResponseObject<Questions> deleteQuestionsById(@RequestParam(value = "id", required = true) int id) {

		return questionsservice.deleteQuestionsById(id);

	}

	@PostMapping("/answer")
	public ResponseObject<String> answer(@RequestBody AnswerQr answerQr) {
		return questionsservice.answer(answerQr);

	}

}