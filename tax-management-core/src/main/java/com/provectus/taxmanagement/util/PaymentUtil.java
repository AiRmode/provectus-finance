package com.provectus.taxmanagement.util;

import com.provectus.taxmanagement.entity.TaxRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

/**
 * Created by agricenko on 10.09.2017.
 */
@Component
public class PaymentUtil {

    public void parseDocument(File document) throws IOException {
        Document doc = Jsoup.parse(document, "UTF-8");

        Elements tables = doc.getElementsByTag("table");

        if (tables.size() != 2) System.out.println("Table counts is not correct");

        String headerText = parseHeader(tables.get(0));
        parseTable(tables.get(1));
    }

    private String parseHeader(Element element) {
        return element.text();
    }

    private List<TaxRecord> parseTable(Element element) {
        List<TaxRecord> taxRecords = new ArrayList<>();
        
        Elements trs = element.getElementsByTag("tr");
        for (int i = 1; i < trs.size() - 1; i++) {
            Element row = trs.get(i);
            Elements tDs = row.getElementsByTag("td");

            TaxRecord taxRecord = parseTaxRecordRaw(tDs);
        }

        return taxRecords;
    }

    private TaxRecord parseTaxRecordRaw(Elements tDs) {
        TaxRecord taxRecord = new TaxRecord();

        String number = tDs.get(0).text();
        String receivingDay = tDs.get(1).text();
        String receivingTime = tDs.get(2).text();
        double incomeAmount = parseNumber(tDs.get(3).text());
        double consumptionAmount = parseNumber(tDs.get(4).text());
        String currency = tDs.get(5).text();
        String paymentPurpose = tDs.get(5).text();
        String YEGRPOU = tDs.get(7).text();
        String counterparty = tDs.get(8).text();
        String bill = tDs.get(9).text();
        String MFO = tDs.get(10).text();
        String reference = tDs.get(11).text();

        return taxRecord;
    }

    private double parseNumber(String text) {
        if (text.isEmpty()) return 0;

        return Double.parseDouble(text);
    }
}
