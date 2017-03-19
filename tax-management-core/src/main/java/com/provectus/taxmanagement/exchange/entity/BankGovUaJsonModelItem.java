package com.provectus.taxmanagement.exchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by alexey on 12.03.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankGovUaJsonModelItem implements Serializable {
    private Integer r030;
    private String txt;
    private Double rate;
    private String cc;
    private String exchangedate;

    public BankGovUaJsonModelItem() {
    }

    public Integer getR030() {
        return r030;
    }

    public void setR030(Integer r030) {
        this.r030 = r030;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getExchangedate() {
        return exchangedate;
    }

    public void setExchangedate(String exchangedate) {
        this.exchangedate = exchangedate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankGovUaJsonModelItem that = (BankGovUaJsonModelItem) o;

        if (r030 != null ? !r030.equals(that.r030) : that.r030 != null) return false;
        if (txt != null ? !txt.equals(that.txt) : that.txt != null) return false;
        if (rate != null ? !rate.equals(that.rate) : that.rate != null) return false;
        if (cc != null ? !cc.equals(that.cc) : that.cc != null) return false;
        return !(exchangedate != null ? !exchangedate.equals(that.exchangedate) : that.exchangedate != null);

    }

    @Override
    public int hashCode() {
        int result = r030 != null ? r030.hashCode() : 0;
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        result = 31 * result + (cc != null ? cc.hashCode() : 0);
        result = 31 * result + (exchangedate != null ? exchangedate.hashCode() : 0);
        return result;
    }
}
