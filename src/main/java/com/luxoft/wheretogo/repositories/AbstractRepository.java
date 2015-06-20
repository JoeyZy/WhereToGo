package com.luxoft.wheretogo.repositories;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractRepository<T> {

	private final Class<T> clazz;
	@Autowired
	private SessionFactory sessionFactory;

	public AbstractRepository(Class<T> clazz) {
		this.clazz = clazz;
	}

	protected List<T> findAll() {
		Criteria criteria = getCriteria();
		return criteria.list();
	}

	protected void add(T object) {
		sessionFactory.getCurrentSession().persist(object);
	}

	protected T findByProperty(String property, Object value) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq(property, value));
		return (T) criteria.uniqueResult();
	}

	private Criteria getCriteria() {
		return sessionFactory.getCurrentSession().createCriteria(clazz).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	}
}
