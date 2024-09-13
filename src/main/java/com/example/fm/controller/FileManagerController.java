package com.example.fm.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.fm.service.FileService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class FileManagerController {

	@Autowired
	FileService fileService;
	public static final Logger log = Logger.getLogger(FileManagerController.class.getName());

	@PostMapping("upload-file")
	public boolean uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			fileService.saveFile(file);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info("Error : "+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	@GetMapping("download")
	public ResponseEntity<Resource> getMethodName(@RequestParam("file") String file) {
		 File downloadedFile;
		try {
			downloadedFile = fileService.getDownloadedFile(file);
			return ResponseEntity.ok()
					.contentLength(downloadedFile.length())
					.body(new InputStreamResource(Files.newInputStream(downloadedFile.toPath())))
					;
			//.build();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.badRequest().build();
	}
}