package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.service.ExchangeRateService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by alexey on 10.03.17.
 */
@Service
public class ExchangeRateUahServiceImpl implements ExchangeRateService {

    @Override
    public Double getRate(Date date, String currencyCode) {
        return null;
    }

    @Override
    public Double getTodaysRate(String currencyCode) {
        return null;
    }
}
