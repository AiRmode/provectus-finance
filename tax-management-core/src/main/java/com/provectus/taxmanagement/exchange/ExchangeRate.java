package com.provectus.taxmanagement.exchange;

import java.util.Date;

/**
 * Created by alexey on 12.03.17.
 */
public interface ExchangeRate {
    String getCurrencyType();

    void setCurrencyType(String currencyType);

    Double getRate();

    void setRate(Double rate);

    Date getExchangeRateDate();

    void setExchangeRateDate(Date exchangeRateDate);

    String getRateProviderResource();

    void setRateProviderResource(String rateProviderResource);
}
