package com.vocabpocker.dao;

import java.io.Serializable;

import java.lang.reflect.ParameterizedType;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao<PK extends Serializable, T> {

	private final Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public AbstractDao() {
		
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T getByKey(PK key) {
		return (T) getSession().get(persistentClass, key);
	}

	public void persist(T entity) {
		getSession().persist(entity);
	}

	public void update(T entity) {
		getSession().update(entity);
	}
	
	public void merge(T entity) {
		getSession().merge(entity);
	}
	
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}
	
	public void delete(T entity) {
		getSession().delete(entity);
	}

	public void deleteById(Long id) {
		String sql = "delete from " + persistentClass.getName() + " where id = :id";
		System.out.println("deleteById sql=" + sql);
		Query query = getSession().createSQLQuery(sql);
		query.setLong("id", id);
		query.executeUpdate();
	}

	public boolean isActive(Long id) {
		Boolean active = (Boolean) getSession()
				.createQuery("select active from " + persistentClass.getName() + " where id = :id").setLong("id", id)
				.uniqueResult();
		return active.booleanValue();
	}

	protected Criteria createEntityCriteria() {
		return getSession().createCriteria(persistentClass);
	}

}
