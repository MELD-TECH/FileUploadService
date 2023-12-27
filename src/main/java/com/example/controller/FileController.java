package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.StorageServiceImpl;

@RestController
public class FileController {

	@Autowired
	private StorageServiceImpl service;
	
	@PostMapping("/upload")
	public ResponseEntity<Object> storeFiles(@RequestParam MultipartFile file){
		
		try {
		String filename = service.save(file);
		
		return new ResponseEntity<Object>(filename, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>("Unable to save file ", HttpStatus.BAD_REQUEST);
		}
		}
}
