package com.provectus.taxmanagement.service;

import com.provectus.taxmanagement.entity.ExchangeRateUah;
import com.provectus.taxmanagement.integration.TestParent;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by alexey on 17.04.17.
 */
public class ExchangeRateUahServiceTest extends TestParent {

    @Test
    public void testRateByDate() throws ParseException, URISyntaxException, MalformedURLException {
        String stringDate = "20170414";
        String currencyCode = "USD";
        Double usd = exchangeRateService.getRate(stringDate, currencyCode);//20170304
        assertEquals(usd, new Double(26.864209));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = dateFormat.parse(stringDate);
        ExchangeRateUah rateFromDB = exchangeRatesUahRepository.findByCurrencyTypeAndExchangeRateDate(currencyCode, new java.sql.Date(date.getTime()));
        assertNotNull(rateFromDB);
        assertEquals(usd, rateFromDB.getRate());
    }
}
