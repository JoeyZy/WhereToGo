package com.luxoft.wheretogo.repositories;

import java.util.List;

import com.luxoft.wheretogo.models.Currency;

public interface CurrenciesRepository {

	List<Currency> findAll();

	Currency findById(long categoryId);
}
