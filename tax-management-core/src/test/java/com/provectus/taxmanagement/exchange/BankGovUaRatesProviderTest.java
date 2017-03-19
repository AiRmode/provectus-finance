package com.provectus.taxmanagement.exchange;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by alexey on 12.03.17.
 */
public class BankGovUaRatesProviderTest {

    @Test
    public void test() throws ParseException, MalformedURLException, URISyntaxException {
        ExchangeRatesProviderFactory factory = ExchangeRatesProviderFactory.createExchangeProvider("");
        ExchangeRate rates = factory.getRates("USD", "20170304");
        assertNotNull(rates);
        assertEquals(rates.getCurrencyType(), "USD");
        assertEquals(rates.getExchangeRateDate(), new SimpleDateFormat(BankGovUaUahExchangeRatesProvider.BANK_GOV_UA_DATE_FORMAT).parse("04.03.2017"));
        assertEquals(rates.getRate(), new Double(27.091316));
        assertEquals(rates.getRateProviderResource(), BankGovUaUahExchangeRatesProvider.BASE_URL);
    }
}
