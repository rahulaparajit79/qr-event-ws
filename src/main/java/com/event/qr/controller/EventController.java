package com.event.qr.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.event.qr.model.Base64QR;
import com.event.qr.model.Event;
import com.event.qr.service.EventService;
import com.event.qr.util.Base64EncoderDecoder;
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

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/event")
public class EventController {

	@Autowired
	EventService eventservice;

	@GetMapping("/")
	public String getEventWelcome() {
		return "This is event controller.";
	}

	@PostMapping("/add")
	public ResponseObject<Event> saveEvent(@RequestBody Event event) {

		return eventservice.saveEvent(event);
	}

	@GetMapping("/getbyid")
	public ResponseObject<Event> getEventById(@RequestParam(value = "id", required = true) int id) {

		return eventservice.getEventById(id);

	}

	@GetMapping("/listall")
	public ResponseList<Event> getEventListAll() {

		return eventservice.getAllEvents();
	}
	
	@GetMapping("/listall/pending")
	public ResponseList<Event> getEventListAllPending() {

		return eventservice.getAllPendingEvents();
	}

	@PostMapping("/update")
	public ResponseObject<Event> updateEvent(@RequestBody Event event) {

		return eventservice.updateEvent(event);
	}
	
	@PostMapping("/activate")
	public ResponseObject<Event> activateEvent(@RequestBody Event event) {

		return eventservice.activateEvent(event);
	}
	
	@GetMapping("/current")
	public ResponseObject<Event> getCurrentEvent() {

		return eventservice.getCurrentEvent();
	}
	
	@PostMapping("/disable")
	public ResponseObject<Event> disableEvent(@RequestBody Event event) {

		return eventservice.disableEvent(event);
	}

	@DeleteMapping("/delete")
	public ResponseObject<Event> deleteEventById(@RequestParam(value = "id", required = true) int id) {

		return eventservice.deleteEventById(id);

	}

	@PostMapping("/scanqr")
	public ResponseObject<String> scanqr(@RequestBody Base64QR base64qr) {

		ResponseObject<String> response = new ResponseObject<>();
		String encodedImg = base64qr.getQrCode();
		File imgFile = null;

		try {
			imgFile = File.createTempFile("qrImage", ".png");
			Path destinationFile = imgFile.toPath();
			byte[] decodedImg = DatatypeConverter.parseBase64Binary(encodedImg);
			Files.write(destinationFile, decodedImg);
			String result = decodeQRCode(imgFile);
			response.setResponseCode(ResponseCodes.SUCCESS.getResponseCode());
			response.setResponseData(result);
			response.setResponseDesc(result);
			imgFile.delete();
		} catch (IOException e) {
			e.printStackTrace();

			response.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			response.setResponseDesc(e.getMessage());
			return response;
		} catch (Exception e) {
			response.setResponseCode(ResponseCodes.FAILURE.getResponseCode());
			response.setResponseDesc(e.getMessage());
			return response;
		}

		return response;
	}

	private static String decodeQRCode(File qrCodeimage) {
		try {
			BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
			LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			Result result = new MultiFormatReader().decode(bitmap);
			return result.getText();
		} catch (NotFoundException e) {
			System.out.println("There is no QR code in the image");
			return "There is no QR code in the image";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return e.getMessage();
		}
	}

}