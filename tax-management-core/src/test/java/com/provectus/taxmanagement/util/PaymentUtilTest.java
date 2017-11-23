package com.provectus.taxmanagement.util;

import com.provectus.taxmanagement.entity.TaxRecord;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by agricenko on 10.09.2017.
 */
public class PaymentUtilTest {

    URL resource = Thread.currentThread().getContextClassLoader().getResource("statements.xls");

    public List<TaxRecord> getStubRecords() throws ParseException {
        List<TaxRecord> taxRecords = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat(PaymentUtil.DATE_FORMAT);

        TaxRecord taxRecord = new TaxRecord();
        taxRecord.setUahRevenue(-30.0);
        taxRecord.setReceivingDate(dateFormat.parse("01.09.2017 15:47:00"));
        taxRecords.add(taxRecord);

        taxRecord = new TaxRecord();
        taxRecord.setUahRevenue(-10.50);
        taxRecord.setReceivingDate(dateFormat.parse("22.08.2017 11:19:00"));
        taxRecords.add(taxRecord);

        taxRecord = new TaxRecord();
        taxRecord.setUahRevenue(9000.50);
        taxRecord.setReceivingDate(dateFormat.parse("21.08.2017 12:25:00"));
        taxRecords.add(taxRecord);

        taxRecord = new TaxRecord();
        taxRecord.setUahRevenue(-4000.00);
        taxRecord.setReceivingDate(dateFormat.parse("18.08.2017 10:04:00"));
        taxRecords.add(taxRecord);

        taxRecord = new TaxRecord();
        taxRecord.setUahRevenue(255600.00);
        taxRecord.setReceivingDate(dateFormat.parse("15.08.2017 10:42:00"));
        taxRecords.add(taxRecord);

        return taxRecords;
    }

    @Test
    public void testParseDocument() throws IOException, ParseException {
        PaymentUtil paymentUtil = new PaymentUtil();
        List<TaxRecord> taxRecords = paymentUtil.parseDocument(new File(resource.getPath()));
        assertEquals(taxRecords, getStubRecords());
    }
}
