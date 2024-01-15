package com.vocabpocker.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vocabpocker.dto.UserWrapper;
import com.vocabpocker.model.User;
import com.vocabpocker.service.UserService;
import com.vocabpocker.util.AppUtils;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = { "/save" }, method = RequestMethod.POST)
	@ResponseBody
	public UserWrapper saveUser(@Valid User user, BindingResult result, ModelMap model) {

		if(result.hasErrors()){
			System.out.println(result.getAllErrors());
		}
		userService.saveOrUpdate(user);
		return getUser(user.getId(), model);
	}

	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
	@ResponseBody
	public UserWrapper getUser(@PathVariable("id") Long id, ModelMap model) {

		User user = null;
		UserWrapper userWrapper = null;

		if (null != id && id != 0) {
			user = userService.findById(id);
			userWrapper = userService.toUserWrapper(user);
		} else {
			userWrapper = new UserWrapper();
			userWrapper.setJoiningDate(AppUtils.getCurrentDateAsString());
		}
		model.addAttribute("user", userWrapper);

		return userWrapper;
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public Set<UserWrapper> listUsers(ModelMap model) {
		Set<User> users = userService.findAllActive();
		return userService.toUserWrapper(users);
	}
}
