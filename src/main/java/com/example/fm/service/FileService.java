package com.example.fm.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.fm.controller.FileManagerController;

@Service
public class FileService {

	String storageDirectory = "C:\\Users\\LambodarThakur\\Downloads\\storage";
	public static final Logger log = Logger.getLogger(FileService.class.getName());

	public void saveFile(MultipartFile fileTosave) throws IOException {

		if (fileTosave == null) {
			throw new NullPointerException("File is Empty");
		}

		var targetFile = new File(storageDirectory + File.separator + fileTosave.getOriginalFilename());
		if (!Objects.equals(targetFile.getParent(), storageDirectory)) {
			throw new SecurityException("Unsupported File");
		}
		Files.copy(fileTosave.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	}
	
	public File getDownloadedFile(String filename) throws Exception{
		if (filename == null) {
			throw new NullPointerException("File is Empty");
		}

		var targetFile = new File(storageDirectory + File.separator + filename);
		log.info("TargetFile :: "+targetFile);
		if (!Objects.equals(targetFile.getParent(), storageDirectory)) {
			throw new SecurityException("Unsupported File");
		}
		return targetFile;
	}
}
