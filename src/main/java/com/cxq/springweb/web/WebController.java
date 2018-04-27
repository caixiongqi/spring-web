package com.cxq.springweb.web;

		import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxq.springweb.bean.JsonResult;
import com.cxq.springweb.bean.PageQueryParam;
import com.cxq.springweb.bean.User;
import com.cxq.springweb.service.UserService;

@Controller
@RequestMapping(value = "web")
public class WebController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String toIndex(Model model) {
		model.addAttribute("index", "hello world");
		return "index";
	}

	@RequestMapping(value = "/index2", method = RequestMethod.GET)
	@ResponseBody
	public String toIndex2() {
		return "hello world";
	}

	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public String toUserList(Model model) {
		model.addAttribute("userList", userService.getUserList());
		return "userList";
	}

	@RequestMapping(value = "/getUserList", method = RequestMethod.GET)
	@ResponseBody
	public List<User> toUserList() {
		return userService.getUserList();
	}

	@RequestMapping(value = "/pageUserList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult getUserListPage(PageQueryParam pageQueryParam, User user) {
		return userService.getUserListPage(pageQueryParam, user);
	}

	@RequestMapping(value = "/userPage")
	public String toUserPage() {
		return "userListPage";
	}

	/**
	 * 创建或者修改用户的接口
	 * @param user 用户实体
	 * @return 用户列表页面
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	/**
	 * 批量删除用户接口
	 * @param ids 用户id的字符串集合， 格式为 (id1, id2, id3)
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteUser(String ids) {
		userService.deleteUser(ids);
		return "删除成功!";
	}
	
	
	/**
	 * 跳转到创建用户页面接口
	 * @return 
	 */
	@RequestMapping(value = "/createPage")
	public String toCreatePage(@RequestParam(value = "id", required = false) String id, Model model) {
		if (id != null) {
			model.addAttribute("user", userService.getUserById(id));
		}
		return "createUserPage";
	}


}
