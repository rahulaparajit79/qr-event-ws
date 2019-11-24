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
import com.event.qr.util.ResponseList;
import com.event.qr.util.ResponseCodes;

import com.event.qr.service.ImagesService;
import com.event.qr.model.Images;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/images")
public class ImagesController {

	@Autowired
	ImagesService imagesservice;

	@PostMapping("/add")
	public ResponseObject<Images> saveImages(@RequestBody Images images) {

		return imagesservice.saveImages(images);
	}

	@GetMapping("/getbyimageid")
	public ResponseObject<Images> getImagesByImageId(@RequestParam(value = "imageid", required = true) int imageid) {

		return imagesservice.getImagesByImageId(imageid);

	}

	@GetMapping("/listall")
	public ResponseList<Images> getImagesListAll() {

		return imagesservice.getAllImagess();
	}

	@PutMapping("/update")
	public ResponseObject<Images> updateImages(@RequestBody Images images) {

		return imagesservice.updateImages(images);
	}

	@DeleteMapping("/delete")
	public ResponseObject<Images> deleteImagesByImageId(@RequestParam(value = "imageid", required = true) int imageid) {

		return imagesservice.deleteImagesByImageId(imageid);

	}

}