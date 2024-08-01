package com.shreyas.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shreyas.blog.services.FileServices;

@Service
public class FileServicesImpl implements FileServices{

	@Override
	public String uploadImage(String path, MultipartFile image) throws IOException {
	    String name = image.getOriginalFilename();
	    String randomUid = UUID.randomUUID().toString();
	    String filename = randomUid.concat(name.substring(name.lastIndexOf(".")));
	    String filePath = path + File.separator + filename;
	    
	    File f = new File(path);
	    if(!f.exists()) {
	    	f.mkdir();
	    }
	    
	    Files.copy(image.getInputStream(), Paths.get(filePath));
	    
		return filename;
	}

	@Override
	public InputStream getImage(String path, String imageName) throws FileNotFoundException {
		String filepath = path+File.separator+imageName;
		InputStream is=new FileInputStream(filepath);
		return is;
	}
	
//	public static void main(String[] args) {
//		String name="tttttt.png";
//		String randomUid = UUID.randomUUID().toString();
//	    String filename = randomUid.concat(name.substring(name.lastIndexOf(".")));
//		System.out.println("test "+filename);
//	}
}
