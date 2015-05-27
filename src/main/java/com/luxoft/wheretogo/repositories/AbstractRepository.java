package com.luxoft.wheretogo.repositories;

import java.util.List;

public abstract class AbstractRepository<T> {
	public abstract List<T> findAll();

	public abstract T getById(int id);

	public abstract T getByName(String name);
}
