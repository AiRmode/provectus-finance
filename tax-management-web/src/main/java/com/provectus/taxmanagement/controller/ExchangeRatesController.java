package com.provectus.taxmanagement.controller;

import com.provectus.taxmanagement.entity.ExchangeRateUah;
import com.provectus.taxmanagement.repository.ExchangeRatesUahRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alexey on 28.03.17.
 */
@RestController
@RequestMapping(value = "/exchangeRatesUah")
public class ExchangeRatesController {
    private ExchangeRatesUahRepository exchangeRatesUahRepository;

    public ExchangeRateUah getRatesByDate() {
        return null;
    }

}
