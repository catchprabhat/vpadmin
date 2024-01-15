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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vocabpocker.dto.WordWrapper;
import com.vocabpocker.model.Word;
import com.vocabpocker.model.WordGroup;
import com.vocabpocker.service.WordGroupService;
import com.vocabpocker.service.WordService;

@Controller
@RequestMapping("/word")
public class WordController {

	@Autowired
	WordService wordService;
	
	@Autowired
	WordGroupService wordGroupService;

	@RequestMapping(value = { "/save" }, method = RequestMethod.POST)
	@ResponseBody
	public WordWrapper saveWord(@Valid Word word, BindingResult result, ModelMap model,
			@RequestParam("groupId") Long groupId) {

		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
		}
		word.setWordGroup(wordGroupService.findById(groupId));
		wordService.saveOrUpdate(word);
		return getWord(word.getId(), model);
	}

	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
	@ResponseBody
	public WordWrapper getWord(@PathVariable("id") Long id, ModelMap model) {

		Word word = null;
		WordWrapper wordWrapper = null;

		if (null != id && id != 0) {
			word = wordService.findById(id);
			wordWrapper = wordService.toWordWrapper(word);
		} else {
			wordWrapper = new WordWrapper();
		}
		model.addAttribute("word", wordWrapper);

		return wordWrapper;
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public Set<WordWrapper> listWords(ModelMap model) {
		Set<Word> words = wordService.findAllActive();
		return wordService.toWordWrapper(words);
	}
	
	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public String listWordsView(ModelMap model) {
		
		Set<WordGroup> wordGroups = wordGroupService.findAllActive();
		model.addAttribute("wordGroups", wordGroups);
		
		Set<Word> words = wordService.findAllActive();
		Set<WordWrapper> wordsWrapper = wordService.toWordWrapper(words);
		model.addAttribute("words", wordsWrapper);
		return "words";
	}
}
