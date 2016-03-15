package com.luxoft.wheretogo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luxoft.wheretogo.models.Currency;
import com.luxoft.wheretogo.repositories.CurrenciesRepository;

@Service
@Transactional
public class CurrenciesServiceImpl implements CurrenciesService {

	@Autowired
	private CurrenciesRepository currenciesRepository;

	@Override
	public List<Currency> findAll() {
		return currenciesRepository.findAll();
	}

	@Override
	public Currency findById(long categoryId) {
		return currenciesRepository.findById(categoryId);
	}
}
