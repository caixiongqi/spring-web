package com.cxq.springweb.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${com.title}")
	private String title;

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
		return "主题为： " + title;
	}
	
	
	/**
	 * 跳转到用户集合页面
	 * @param model 用于向前端传送数据的model
	 * @return 用户集合页面路径
	 */
	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public String toUserList(Model model) {
		model.addAttribute("userList", userService.getUserList());
		return "userList";
	}

	/**
	 * 获取用户集合（ajax方式）
	 * @return  用户集合
	 */
	@RequestMapping(value = "/getUserList", method = RequestMethod.GET)
	@ResponseBody
	public List<User> toUserList() {
		return userService.getUserList();
	}

	/**
	 * 获取用户分页数据
	 * @param pageQueryParam 接收到的分页信息
	 * @param user 用户信息，条件查询
	 * @return 分页数据
	 */
	@RequestMapping(value = "/pageUserList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult getUserListPage(PageQueryParam pageQueryParam, User user) {
		return userService.getUserListPage(pageQueryParam, user);
	}

	/**
	 * 跳转到用户分页界面
	 * @return
	 */
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
