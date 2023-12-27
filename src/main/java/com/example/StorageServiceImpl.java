package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.exception.ResponseException;

@Service
public class StorageServiceImpl implements StorageService {

	private Path root = Paths.get("Upload-Directory");
	
	
	@Override
	public String save(MultipartFile file) {
		// TODO Auto-generated method stub

//		deleteAll();
//		init();
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		if(fileName.isEmpty() || fileName.length() == 0) {
			
			throw new ResponseException("File cannot be empty");		
		}
		
	try {
		Path uploadPath = Paths.get("Upload-Directory");
		
		if(!Files.exists(uploadPath)) {
			Files.createDirectory(uploadPath);
		}
		
		Path destinationFile = this.root.resolve(Paths.get(fileName)).normalize().toAbsolutePath();
		
		System.out.println("Destination path " + destinationFile);
		
		InputStream filestream = file.getInputStream();
		
		Files.copy(filestream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
		
	}catch(IOException e) {
		throw new ResponseException("unable to save file", e);
	}
		return fileName;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

		try {
			Files.createDirectories(root);
		}catch(IOException ex) {
			throw new ResponseException("Unable to create directory ", ex.getCause());
		}
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

		FileSystemUtils.deleteRecursively(root.toFile());
	}

	@Override
	public Resource getResource(String filename) {
		// TODO Auto-generated method stub
		
		try {
			Path file = this.root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}else {
				throw new ResponseException("resource does not exist");
			}
			
		}catch(MalformedURLException ex) {
			throw new ResponseException("malformed resource file ", ex.getCause());
		}
		
	}

}
