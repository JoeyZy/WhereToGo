package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.model.Model;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractRepository<T extends Model> {

	private final Class<T> clazz;
	@Autowired
	private SessionFactory sessionFactory;

	public AbstractRepository(Class<T> clazz) {
		this.clazz = clazz;
	}

	public List<T> findAll() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
		return criteria.list();
	}

	public void add(T element) {
		sessionFactory.getCurrentSession().persist(element);
	}

	public T getById(int id) {
		for (T t : findAll()) {
			if (t.getId() == id) {
				return t;
			}
		}
		return null;
	}

	public T getByName(String name) {
		for (T t : findAll()) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}
}
