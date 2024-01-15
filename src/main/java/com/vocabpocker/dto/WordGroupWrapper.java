package com.vocabpocker.dto;

import java.util.Set;

public class WordGroupWrapper extends BaseDtoWrapper {

	private String name;
	private Set<WordWrapper> words;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<WordWrapper> getWords() {
		return words;
	}

	public void setWords(Set<WordWrapper> words) {
		this.words = words;
	}

	

}
