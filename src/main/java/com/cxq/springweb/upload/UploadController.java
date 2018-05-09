package com.cxq.springweb.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "upload")
public class UploadController {
	
	
	@RequestMapping(value = "/toUpload")
	public String toUploadPage() {
		return "upload/upload";
	}
	
	@RequestMapping(value = "/file")
	@ResponseBody
	public String upload(@RequestParam MultipartFile file) throws IOException {
		InputStream fileStream = file.getInputStream();
		File resultFile = new File("D:\\test.docx");
		FileUtils.copyInputStreamToFile(fileStream, resultFile);
        long size = file.getSize();
        return String.valueOf(size);
	}

}
