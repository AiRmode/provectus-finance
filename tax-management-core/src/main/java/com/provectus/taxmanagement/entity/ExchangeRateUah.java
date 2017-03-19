package com.provectus.taxmanagement.entity;

import com.provectus.taxmanagement.exchange.ExchangeRate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by alexey on 11.03.17.
 */
@Document(collection = "exchangeRatesUah")
public class ExchangeRateUah implements ExchangeRate, Serializable {
    @Id
    private String id;
    private String currencyType; //USD, EUR
    private Double rate;
    private Date exchangeRateDate;
    private String rateProviderResource;//web site, that provided the exchange rate

    private Date createdDate;
    private Date modifiedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getCurrencyType() {
        return currencyType;
    }

    @Override
    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    @Override
    public Double getRate() {
        return rate;
    }

    @Override
    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public Date getExchangeRateDate() {
        return exchangeRateDate;
    }

    @Override
    public void setExchangeRateDate(Date exchangeRateDate) {
        this.exchangeRateDate = exchangeRateDate;
    }

    @Override
    public String getRateProviderResource() {
        return rateProviderResource;
    }

    @Override
    public void setRateProviderResource(String rateProviderResource) {
        this.rateProviderResource = rateProviderResource;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExchangeRateUah that = (ExchangeRateUah) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (currencyType != null ? !currencyType.equals(that.currencyType) : that.currencyType != null) return false;
        if (rate != null ? !rate.equals(that.rate) : that.rate != null) return false;
        if (exchangeRateDate != null ? !exchangeRateDate.equals(that.exchangeRateDate) : that.exchangeRateDate != null)
            return false;
        if (rateProviderResource != null ? !rateProviderResource.equals(that.rateProviderResource) : that.rateProviderResource != null)
            return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        return !(modifiedDate != null ? !modifiedDate.equals(that.modifiedDate) : that.modifiedDate != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (currencyType != null ? currencyType.hashCode() : 0);
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        result = 31 * result + (exchangeRateDate != null ? exchangeRateDate.hashCode() : 0);
        result = 31 * result + (rateProviderResource != null ? rateProviderResource.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        return result;
    }
}
