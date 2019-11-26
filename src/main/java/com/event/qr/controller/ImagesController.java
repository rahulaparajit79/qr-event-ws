package com.event.qr.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.event.qr.model.Images;
import com.event.qr.service.FileStorageService;
import com.event.qr.service.ImagesService;
import com.event.qr.util.ResponseList;
import com.event.qr.util.ResponseObject;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/images")
public class ImagesController {

	@Autowired
	ImagesService imagesservice;
	@Autowired
	private FileStorageService fileStorageService;

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

	@CrossOrigin(origins = "*", methods = {RequestMethod.POST,RequestMethod.GET})
	@PostMapping("/upload/multi")
	public ResponseEntity<?> uploadFileMulti(
			@RequestParam("files") MultipartFile[] uploadfiles) {

		System.out.println("Multiple file upload!");


		// Get file name
		String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
				.filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

		if (StringUtils.isEmpty(uploadedFileName)) {
			return new ResponseEntity("please select a file!", HttpStatus.OK);
		}

		try {

			fileStorageService.saveUploadedFiles(Arrays.asList(uploadfiles));

		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity("Successfully uploaded - "
				+ uploadedFileName, HttpStatus.OK);

	}

}