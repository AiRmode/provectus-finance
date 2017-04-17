package com.provectus.taxmanagement.service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * Created by alexey on 10.03.17.
 */
public interface ExchangeRateService {
    Double getRate(String date, String currencyCode) throws ParseException, MalformedURLException, URISyntaxException;
    Double getTodaysRate(String currencyCode);
}
