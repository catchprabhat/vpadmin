package com.vocabpocker.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vocabpocker.dao.WordGroupDao;
import com.vocabpocker.dto.WordGroupWrapper;
import com.vocabpocker.model.WordGroup;

@Service("wordGroupService")
@Transactional
public class WordGroupServiceImpl implements WordGroupService {

	@Autowired
	private WordGroupDao dao;

	@Autowired
	WordService wordService;

	public WordGroup findById(Long id) {
		return dao.findById(id);
	}

	public void saveOrUpdate(WordGroup t) {
		if (null != t.getId()) {
			WordGroup savedEntity = findById(t.getId());
			savedEntity.setActive(t.getActive());
			savedEntity.setName(t.getName());
			savedEntity.setWords(t.getWords());
			dao.update(t);
		} else {
			dao.persist(t);
		}
	}

	public void deleteById(Long id) {
		dao.deleteById(id);
	}

	public Set<WordGroup> findAll() {
		return dao.findAll();
	}

	public Set<WordGroup> findAllActive() {
		return dao.findAllActive();
	}

	public boolean isActive(Long id) {
		return dao.isActive(id);
	}

	public Map<Long, WordGroup> findByIds(Collection<Long> ids) {
		Map<Long, WordGroup> wordGroupsMap = new HashMap<Long, WordGroup>();
		List<WordGroup> wordGroups = dao.findByIds(ids);

		if (!wordGroups.isEmpty()) {
			for (WordGroup wordGroup : wordGroups) {
				wordGroupsMap.put(wordGroup.getId(), wordGroup);
			}
		}
		return wordGroupsMap;
	}

	public WordGroupWrapper toWordGroupWrapper(WordGroup wordGroup) {
		WordGroupWrapper wordGroupWrapper = new WordGroupWrapper();
		if (null != wordGroup) {
			wordGroupWrapper.setId(wordGroup.getId());
			wordGroupWrapper.setActive(wordGroup.getActive());
			wordGroupWrapper.setName(wordGroup.getName());
			wordGroupWrapper.setWords(wordService.toWordWrapper(wordGroup.getWords()));
		}
		return wordGroupWrapper;
	}

	public Set<WordGroupWrapper> toWordGroupWrapper(Set<WordGroup> wordGroups) {
		Set<WordGroupWrapper> wrapperList = new LinkedHashSet<WordGroupWrapper>();
		if (null != wordGroups && !wordGroups.isEmpty()) {
			for (WordGroup wordGroup : wordGroups) {
				wrapperList.add(toWordGroupWrapper(wordGroup));
			}
		}
		return wrapperList;
	}
}
