package com.cxq.springweb.interactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxq.springweb.bean.User;

/**
 * 前后端交互两种方式：1.跳转页面，如form表单提交；2.restful接口，ajax提交
 *
 */
@Controller
@RequestMapping(value = "interactive")
public class InteractiveController {
	
	private static Logger logger = LoggerFactory.getLogger(InteractiveController.class);
	
	
	@RequestMapping(value = "/forwardPage")
	public String toFormPage() {
		return "interactive/forward";
	}
	
	/**
	 * 跳转页面交互方式
	 * @return
	 */
	@RequestMapping(value = "/forward")
	public String saveUser(User user, Model model) {
		
		logger.info("姓名：{}， 年龄：{}， 性别：{}。", user.getName(), user.getAge(), user.getSex());
		
		model.addAttribute("user", user);
		return "interactive/forward";
	}
	
	@RequestMapping(value = "/ajaxPage")
	public String toAjaxPage() {
		return "interactive/ajax";
	}
	
	/**
	 * restful接口 ajax交互方式
	 * @return
	 */
	@RequestMapping(value = "/ajax", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public User save(@RequestBody User user) {
		
		logger.info("姓名：{}， 年龄：{}， 性别：{}。", user.getName(), user.getAge(), user.getSex());
		return user;
	}

}
