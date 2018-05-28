package com.provectus.taxmanagement.entity;

import com.provectus.taxmanagement.integration.TestParent;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by alexey on 11.03.17.
 */
public class TaxRecordTest extends TestParent {

    @Test
    public void testUahAmountForTaxInspection() {
        TaxRecord taxRecord = new TaxRecord();
        taxRecord.setExchRateUsdUahNBUatReceivingDate(25d);
        taxRecord.setUsdRevenue(100d);
        Double total = taxCalculationService.calculateTaxation(taxRecord);
        assertEquals(total, new Double(2500));

        taxRecord = new TaxRecord();
        taxRecord.setUahRevenue(1000d);
        total = taxCalculationService.calculateTaxation(taxRecord);
        assertEquals(total, new Double(1000));

        taxRecord = new TaxRecord();
        taxRecord.setExchRateUsdUahNBUatReceivingDate(8d);
        taxRecord.setUsdRevenue(50d);
        taxRecord.setUahRevenue(500d);
        total = taxCalculationService.calculateTaxation(taxRecord);
        assertEquals(total, new Double(900));
    }

    @Test
    public void testTaxValue() {
        TaxRecord taxRecord = new TaxRecord();
        taxRecord.setExchRateUsdUahNBUatReceivingDate(25d);
        taxRecord.setUsdRevenue(100d);
        Double taxValue = taxCalculationService.calculateTaxValue(taxRecord);
        assertEquals(taxValue, new Double(125));


        taxRecord = new TaxRecord();
        taxRecord.setUahRevenue(1000d);
        taxValue = taxCalculationService.calculateTaxValue(taxRecord);
        assertEquals(taxValue, new Double(50));

        taxRecord = new TaxRecord();
        taxRecord.setExchRateUsdUahNBUatReceivingDate(10d);
        taxRecord.setUsdRevenue(50d);
        taxRecord.setUahRevenue(500d);
        taxValue = taxCalculationService.calculateTaxValue(taxRecord);
        assertEquals(taxValue, new Double(50));
    }
}
