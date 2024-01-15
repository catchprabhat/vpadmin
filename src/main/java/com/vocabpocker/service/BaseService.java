package com.vocabpocker.service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.vocabpocker.model.Word;

public interface BaseService<T> {

	T findById(Long id);

	Map<Long, T> findByIds(final Collection<Long> ids);

	void saveOrUpdate(T t);

	void deleteById(Long id);

	Set<T> findAll();

	Set<T> findAllActive();

	boolean isActive(Long id);

}
