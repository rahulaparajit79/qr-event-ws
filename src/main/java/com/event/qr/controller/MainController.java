package com.event.qr.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.event.qr.service.QRCodeService;

@RestController
@RequestMapping("/")
public class MainController {

	@GetMapping("/")
	public String getEventWelcome() {

		return "Welcome to Event QR Game App API. \n v1.2";

	}
	
	@GetMapping("/generateQRCode")
	public ResponseEntity<Resource> getQRCode(@RequestParam(value = "value") String value, HttpServletRequest request) throws Exception {

        Resource resource = QRCodeService.generateQRCodeImage(value, 450, 450);


        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
           ex.printStackTrace();
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	
	@GetMapping("/deleteTempFiles")
	public String deleteTempFiles() {
		
		File[] files = new File("/tmp/").listFiles();
		
		for(int i=0;i<files.length;i++) {
			if(files[i].getName().startsWith("qrcodeImage")) {
				files[i].delete();
			}
		}
		
		return "true";
		
	}

}
