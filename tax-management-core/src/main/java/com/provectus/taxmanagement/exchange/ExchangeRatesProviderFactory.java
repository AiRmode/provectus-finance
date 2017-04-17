package com.provectus.taxmanagement.exchange;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by alexey on 12.03.17.
 */
public abstract class ExchangeRatesProviderFactory {
    public static final String DEFAULT_RESPONSE_FORMAT = "json";

    public static ExchangeRatesProviderFactory createExchangeProvider(String provider) {
        return new BankGovUaUahExchangeRatesProvider();
    }

    public abstract List<ExchangeRate> getAllRates();

    public abstract List<ExchangeRate> getAllRates(String date);

    public abstract ExchangeRate getRate(String currencyCode, String date) throws URISyntaxException, MalformedURLException, ParseException;

}
