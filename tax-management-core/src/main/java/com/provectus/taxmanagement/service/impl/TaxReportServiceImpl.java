package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.service.TaxReportService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by agricenko on 10.09.2017.
 */
@Service("taxReportService")
public class TaxReportServiceImpl implements TaxReportService {

    public static final String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";

    private static final Logger logger = LoggerFactory.getLogger(TaxReportServiceImpl.class);

    @Autowired
    private ExchangeRateUahServiceImpl exchangeRateUahService;

    @Override
    public List<TaxRecord> parseDocument(File document) throws IOException, ParseException {
        try {
            Document doc = Jsoup.parse(document, "UTF-8");

            Elements tables = doc.getElementsByTag("table");

            if (tables.size() != 2) System.out.println("Table counts is not correct");

            String headerText = parseHeader(tables.get(0));
            return parseTable(tables.get(1));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            //TODO wrap exception
            throw e;
        }
    }

    private String parseHeader(Element element) {
        return element.text();
    }

    private List<TaxRecord> parseTable(Element element) throws ParseException {
        List<TaxRecord> taxRecords = new ArrayList<>();

        Elements trs = element.getElementsByTag("tr");
        for (int i = 1; i < trs.size() - 1; i++) {
            Element row = trs.get(i);
            Elements tDs = row.getElementsByTag("td");

            TaxRecord taxRecord = parseTaxRecordRaw(tDs);
            if (taxRecord != null) {
                taxRecords.add(taxRecord);
            }
        }

        return taxRecords;
    }

    private TaxRecord parseTaxRecordRaw(Elements tDs) throws ParseException {
        TaxRecord taxRecord = new TaxRecord();

        String number = tDs.get(0).text();
        String receivingDay = tDs.get(1).text();
        String receivingTime = tDs.get(2).text();
        double incomeAmount = parseNumber(tDs.get(3).text());
        double consumptionAmount = parseNumber(tDs.get(4).text());
        String currency = tDs.get(5).text();
        String paymentPurpose = tDs.get(6).text();
        String YEGRPOU = tDs.get(7).text();
        String counterparty = tDs.get(8).text();
        String bill = tDs.get(9).text();
        String MFO = tDs.get(10).text();
        String reference = tDs.get(11).text();

        taxRecord.setCounterpartyName(counterparty);
        taxRecord.setPaymentPurpose(paymentPurpose);

        if (currency.equalsIgnoreCase("UAH") && incomeAmount > 0) {
            taxRecord.setUahRevenue(incomeAmount);
        } else if (currency.equalsIgnoreCase("USD") && incomeAmount > 0) {
            taxRecord.setUsdRevenue(incomeAmount);
        }

        if (taxRecord.getUahRevenue() == 0 && taxRecord.getUsdRevenue() == 0) {
            return null;
        }

        try {
            DateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            Date receivingDate = simpleDateFormat.parse(receivingDay + " " + receivingTime);

            DateFormat dateFormatForExchange = new SimpleDateFormat(ExchangeRateUahServiceImpl.DATE_FORMAT);
            String format = dateFormatForExchange.format(receivingDate);
            Double rate = exchangeRateUahService.getRate(format, "USD");

            taxRecord.setExchRateUsdUahNBUatReceivingDate(rate);
            taxRecord.setReceivingDate(receivingDate);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            //TODO wrap exception
            throw e;
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }

        return taxRecord;
    }

    private double parseNumber(String text) {
        if (text.isEmpty()) return 0;

        return Double.parseDouble(text);
    }
}
