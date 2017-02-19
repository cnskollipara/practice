package com.demo.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FeasibleSolutionController {
	
	@Autowired
	FeasibleService service;
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody 
	String uploadFileHandler(@RequestParam("file") MultipartFile multipartFile) {
		String name = multipartFile.getOriginalFilename();
		System.out.println("Uploading : " + name);
		if (!multipartFile.isEmpty()) {
			try {
				byte[] bytes = multipartFile.getBytes();

				// Creating the directory to store file
				String rootPath = "/tmp/";
				File file = new File(rootPath + "input.txt");
				multipartFile.transferTo(file);
				return "Feasible Solution : " + service.findSolution(file);
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name
					+ " because the file was empty.";
		}
	}

}
