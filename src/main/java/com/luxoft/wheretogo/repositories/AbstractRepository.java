package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.model.Model;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepository<T extends Model> {

	protected List<T> elementsList = new ArrayList<>();
	private Class<T> clazz; //NOPMD

	public AbstractRepository(Class<T> clazz) {
		this.clazz = clazz;
	}

	public List<T> findAll() {
		return elementsList;
	}

	@Autowired
	private SessionFactory sessionFactory;

	public void add(T element) {
		sessionFactory.getCurrentSession().persist(element);
	}

	public T getById(int id) {
		for (T t : elementsList) {
			if (t.getId() == id) {
				return t;
			}
		}
		return null;
	}

	public T getByName(String name) {
		for (T t : elementsList) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}
}
