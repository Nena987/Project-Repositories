package com.iktpreobuka.project.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadServiceImpl implements UploadService {

	private static String UPLOADED_FOLDER = "D:\\SpringTemp\\";

	@Override
	public String singleImageUpload(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return " ";
		}
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			
		} catch (IOException e) {
			throw e;
		}
		return UPLOADED_FOLDER + file.getOriginalFilename();
	}

}