package com.vocabpocker.dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface BaseDao<T> {

	T findById(Long id);

	List<T> findByIds(Collection<Long> ids);

	void persist(T t);

	void update(T t);
	
	void merge(T t);

	void saveOrUpdate(T t);

	void deleteById(Long id);

	Set<T> findAll();

	Set<T> findAllActive();

	boolean isActive(Long id);

}
