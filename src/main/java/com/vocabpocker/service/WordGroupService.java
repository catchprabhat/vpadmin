package com.vocabpocker.service;

import java.util.Set;

import com.vocabpocker.dto.WordGroupWrapper;
import com.vocabpocker.model.WordGroup;

public interface WordGroupService extends BaseService<WordGroup> {
	WordGroupWrapper toWordGroupWrapper(WordGroup wordGroup);
	Set<WordGroupWrapper> toWordGroupWrapper(Set<WordGroup> wordGroups);
}
