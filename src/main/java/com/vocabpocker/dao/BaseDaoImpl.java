package com.vocabpocker.dao;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class BaseDaoImpl<T> extends AbstractDao<Long, T> implements BaseDao<T> {

	public T findById(Long id) {
		return super.getByKey(id);
	}

	public void persist(T t) {
		super.persist(t);
	}
	public void update(T t) {
		super.update(t);
	}
	public void merge(T t) {
		super.merge(t);
	}
	public void saveOrUpdate(T t) {
		super.saveOrUpdate(t);
	}

	public void deleteById(Long id) {
		super.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	public Set<T> findAll() {
		Criteria criteria = super.createEntityCriteria();
		return (new HashSet<T>(criteria.list()));
	}

	@SuppressWarnings("unchecked")
	public Set<T> findAllActive() {
		Criteria criteria = super.createEntityCriteria();
		criteria.add(Restrictions.eq("active", true));
		return new HashSet<T>(criteria.list());
	}

	public boolean isActive(Long id) {
		return super.isActive(id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByIds(Collection<Long> ids) {
		Criteria criteria = super.createEntityCriteria();
		criteria.add(Restrictions.in("id", ids));
		return (List<T>) criteria.list();
	}
}
