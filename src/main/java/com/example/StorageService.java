package com.example;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	public String save(MultipartFile file);
	
	public void init();
	
	public void deleteAll();
	
	public Resource getResource(String filename);
}
