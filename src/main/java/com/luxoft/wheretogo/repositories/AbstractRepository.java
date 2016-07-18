package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class AbstractRepository<T> {

	private final Class<T> clazz;
	@Autowired
	protected SessionFactory sessionFactory;

	public AbstractRepository(Class<T> clazz) {
		this.clazz = clazz;
	}

	protected List<T> findAll(String sortColumn) {
		Criteria criteria = getCriteria();
		if (sortColumn != null) {
			criteria.addOrder(Order.asc(sortColumn));
		}
		return criteria.list();
	}

	protected void add(T object) {
		sessionFactory.getCurrentSession().save(object);
	}

	protected void merge(T object) {
		sessionFactory.getCurrentSession().merge(object);
	}

	protected void update(T object) {
		sessionFactory.getCurrentSession().merge(object);
	}

	protected T findByProperty(String property, Object value) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq(property, value));
		return (T) criteria.uniqueResult();
	}

	protected List<T> findListByProperty(String property, Object value) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq(property, value));
		return criteria.list();
	}

	protected Criteria getCriteria() {
		return sessionFactory.getCurrentSession().createCriteria(clazz).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	}

	public List<T> executeCriterion(Criterion criterion) {
		Criteria criteria = getCriteria().add(criterion);
		return criteria.list();
	}
}
