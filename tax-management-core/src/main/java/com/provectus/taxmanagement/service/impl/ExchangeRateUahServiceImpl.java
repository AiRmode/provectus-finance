package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.entity.ExchangeRateUah;
import com.provectus.taxmanagement.exchange.ExchangeRate;
import com.provectus.taxmanagement.exchange.ExchangeRatesProviderFactory;
import com.provectus.taxmanagement.repository.ExchangeRatesUahRepository;
import com.provectus.taxmanagement.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alexey on 10.03.17.
 */
@Service("exchangeRateUahService")
public class ExchangeRateUahServiceImpl implements ExchangeRateService {

    @Autowired
    @Qualifier("exchangeRatesUahRepository")
    private ExchangeRatesUahRepository exchangeRatesUahRepository;

    public static final String DATE_FORMAT = "yyyyMMdd";

    @Override
    public Double getRate(String stringDate, String currencyCode) throws ParseException, MalformedURLException, URISyntaxException {
        ExchangeRateUah exchangeRateUah = getFromDB(currencyCode, stringDate);

        if (exchangeRateUah == null) {//not exist in DB
            ExchangeRateUah rate = getRateByDateFromService(currencyCode, stringDate);
            saveInDB(rate);
            return rate.getRate();
        } else {
            return exchangeRateUah.getRate();
        }
    }

    private void saveInDB(ExchangeRateUah rate) {
        exchangeRatesUahRepository.save(rate);
    }

    private ExchangeRateUah getRateByDateFromService(String currencyCode, String stringDate) throws ParseException, MalformedURLException, URISyntaxException {
        ExchangeRatesProviderFactory exchangeProvider = ExchangeRatesProviderFactory.createExchangeProvider("");
        ExchangeRate rate = exchangeProvider.getRate(currencyCode, stringDate);
        return (ExchangeRateUah) rate;
    }

    private ExchangeRateUah getFromDB(String currencyCode, String stringDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date = dateFormat.parse(stringDate);
        return exchangeRatesUahRepository.findByCurrencyTypeAndExchangeRateDate(currencyCode, new java.sql.Date(date.getTime()));
    }

    @Override
    public Double getTodaysRate(String currencyCode) {
        return null;
    }
}
