package com.shreyas.blog.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileServices {
	String uploadImage(String path,MultipartFile image) throws IOException;
	InputStream getImage(String path,String imageName) throws FileNotFoundException;
}
