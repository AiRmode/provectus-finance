package com.provectus.taxmanagement.integration.repository;

import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.integration.TestParent;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by alexey on 11.03.17.
 */
public class TaxRecordRepositoryTest extends TestParent {

    @Test
    public void testSave() {
        TaxRecord taxRecord = new TaxRecord();
        taxRecord.setUsdRevenue(1d);
        taxRecord.setExchRateUsdUahNBUatReceivingDate(10d);
        taxRecord.setUahRevenue(20d);
        taxRecord.calculateAmountForTaxInspection();
        taxRecord.calculateTaxValue();

        TaxRecord savedRecord = taxRepository.save(taxRecord);
        assertNotNull(savedRecord);

        TaxRecord foundRecord = taxRepository.findOne(savedRecord.getId());
        assertEquals(taxRecord.getUsdRevenue(), foundRecord.getUsdRevenue());
        assertEquals(taxRecord.getExchRateUsdUahNBUatReceivingDate(), foundRecord.getExchRateUsdUahNBUatReceivingDate());
        assertEquals(taxRecord.getUahRevenue(), foundRecord.getUahRevenue());
        assertEquals(taxRecord.getUahAmountForTaxInspection(), foundRecord.getUahAmountForTaxInspection());
        assertEquals(taxRecord.getTaxValue(), foundRecord.getTaxValue());
    }

    @Test
    public void testDelete() {
        TaxRecord taxRecord = new TaxRecord();
        taxRecord.setUsdRevenue(1d);
        taxRecord.setExchRateUsdUahNBUatReceivingDate(10d);
        taxRecord.setUahRevenue(20d);

        TaxRecord savedRecord = taxRepository.save(taxRecord);
        TaxRecord foundRecord = taxRepository.findOne(savedRecord.getId());

        taxRepository.delete(foundRecord);
        TaxRecord deletedRecord = taxRepository.findOne(foundRecord.getId());
        assertNull(deletedRecord);
    }
}