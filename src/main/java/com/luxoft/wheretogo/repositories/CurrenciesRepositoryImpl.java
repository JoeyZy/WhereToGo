package com.luxoft.wheretogo.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.luxoft.wheretogo.models.Currency;

@Repository
public class CurrenciesRepositoryImpl extends AbstractRepository<Currency> implements CurrenciesRepository {

	public CurrenciesRepositoryImpl() {
		super(Currency.class);
	}

	@Override
	public List<Currency> findAll() {
		return super.findAll("id");
	}

	@Override
	public Currency findById(long currId) {
		return super.findByProperty("id", currId);
	}
}
