package com.iktpreobuka.project.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;


public interface UploadService {
	
	public String singleImageUpload(MultipartFile file) throws IOException ;

}
