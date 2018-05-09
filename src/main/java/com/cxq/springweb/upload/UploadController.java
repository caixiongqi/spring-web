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
	
	/**
	 * 跳转到上传文件页面
	 * @return 跳转的页面路径
	 */
	@RequestMapping(value = "/toUpload")
	public String toUploadPage() {
		return "upload/upload";
	}
	
	/**
	 * 上传文件提交
	 * @param file 提交的文件
	 * @return 文件的大小
	 * @throws IOException
	 */
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
