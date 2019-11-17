package com.event.qr.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QRCodeService {

	public static Resource generateQRCodeImage(String text, int width, int height) throws Exception {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

		File tmpFile = new File("qrcodeImage.png");
		Path path = tmpFile.toPath();
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

		try {
			Resource resource = new UrlResource(path.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new Exception("File not found qrcoderImage");
			}
		} catch (MalformedURLException ex) {
			throw new Exception("File not found qrcoderImage," + ex);
		}

	}

}
