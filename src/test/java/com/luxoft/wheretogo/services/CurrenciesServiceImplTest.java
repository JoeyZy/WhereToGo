package com.luxoft.wheretogo.services;

import com.luxoft.wheretogo.models.Currency;
import com.luxoft.wheretogo.repositories.CurrenciesRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Created by maks on 06.07.16.
 */
public class CurrenciesServiceImplTest {

    @Mock
    CurrenciesRepository currenciesRepository;

    @InjectMocks
    CurrenciesServiceImpl currenciesService;

    @Spy
    List<Currency> currencies = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        currencies = getUserList();
    }

    @Test
    public void findAll() throws Exception {
        when(currenciesRepository.findAll()).thenReturn(currencies);
        Assert.assertEquals(currenciesService.findAll(), currencies);
    }

    @Test
    public void findById() throws Exception {
        Currency currency = currencies.get(0);
        when(currenciesRepository.findById(anyLong())).thenReturn(currency);
        Assert.assertEquals(currenciesService.findById(currency.getId()), currency);
    }

    public List<Currency> getUserList() {
        Currency c1 = new Currency();
        c1.setId(1);
        c1.setName("USD");

        Currency c2 = new Currency();
        c2.setId(2);
        c2.setName("RUB");

        currencies.add(c1);
        currencies.add(c2);

        return currencies;
    }

}