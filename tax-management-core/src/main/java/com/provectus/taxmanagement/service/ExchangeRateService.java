package com.provectus.taxmanagement.service;

import java.util.Date;

/**
 * Created by alexey on 10.03.17.
 */
public interface ExchangeRateService {
    Double getRate(Date date, String currencyCode);
    Double getTodaysRate(String currencyCode);
}
