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

import com.vocabpocker.dto.WordGroupWrapper;
import com.vocabpocker.model.WordGroup;
import com.vocabpocker.service.WordGroupService;

@Controller
@RequestMapping("/wordGroup")
public class WordGroupController {

	@Autowired
	WordGroupService wordGroupService;

	@RequestMapping(value = { "/save" }, method = RequestMethod.POST)
	@ResponseBody
	public WordGroupWrapper saveWordGroup(@Valid WordGroup wordGroup, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
		}
		wordGroupService.saveOrUpdate(wordGroup);
		return getWordGroup(wordGroup.getId(), model);
	}

	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
	@ResponseBody
	public WordGroupWrapper getWordGroup(@PathVariable("id") Long id, ModelMap model) {

		WordGroup wordGroup = null;
		WordGroupWrapper wordGroupWrapper = null;

		if (null != id && id != 0) {
			wordGroup = wordGroupService.findById(id);
			wordGroupWrapper = wordGroupService.toWordGroupWrapper(wordGroup);
		} else {
			wordGroupWrapper = new WordGroupWrapper();
		}
		model.addAttribute("wordGroup", wordGroupWrapper);

		return wordGroupWrapper;
	}

	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public String listWordGroupsView(ModelMap model) {
		Set<WordGroup> wordGroups = wordGroupService.findAllActive();
		model.addAttribute("wordGroups", wordGroups);
		return "wordgroup";
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public Set<WordGroupWrapper> listWordGroups(ModelMap model) {
		Set<WordGroup> wordGroups = wordGroupService.findAllActive();
		return wordGroupService.toWordGroupWrapper(wordGroups);
	}
}
