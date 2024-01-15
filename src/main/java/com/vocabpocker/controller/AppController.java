package com.vocabpocker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class AppController {

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String loadIndex(ModelMap model) {
		return "index";
	}

}
