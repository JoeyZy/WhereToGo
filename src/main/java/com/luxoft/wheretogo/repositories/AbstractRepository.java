package com.luxoft.wheretogo.repositories;

import java.util.ArrayList;
import java.util.List;

import com.luxoft.wheretogo.model.Model;

public abstract class AbstractRepository<T extends Model> {

	protected List<T> elementsList = new ArrayList<>();
	private Class<T> clazz;

	public AbstractRepository(Class<T> clazz) {
		this.clazz = clazz;
	}

	public List<T> findAll() {
		return elementsList;
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
