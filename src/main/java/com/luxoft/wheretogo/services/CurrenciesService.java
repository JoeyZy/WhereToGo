package com.luxoft.wheretogo.services;

import java.util.List;

import com.luxoft.wheretogo.models.Currency;

public interface CurrenciesService {
	List<Currency> findAll();

	Currency findById(long currId);
}
