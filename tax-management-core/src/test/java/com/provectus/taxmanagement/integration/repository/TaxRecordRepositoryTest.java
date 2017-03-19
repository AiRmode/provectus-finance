package com.provectus.taxmanagement.integration.repository;

import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.integration.TestParent;
import org.junit.Test;
import org.springframework.dao.OptimisticLockingFailureException;

import static org.junit.Assert.*;

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
        taxRecord.calculateVolumeForTaxInspection();
        taxRecord.calculateTaxValue();

        TaxRecord savedRecord = taxRepository.save(taxRecord);
        assertNotNull(savedRecord);

        TaxRecord foundRecord = taxRepository.findOne(savedRecord.getId());
        assertEquals(taxRecord.getUsdRevenue(), foundRecord.getUsdRevenue());
        assertEquals(taxRecord.getExchRateUsdUahNBUatReceivingDate(), foundRecord.getExchRateUsdUahNBUatReceivingDate());
        assertEquals(taxRecord.getUahRevenue(), foundRecord.getUahRevenue());
        assertEquals(taxRecord.getUahVolumeForTaxInspection(), foundRecord.getUahVolumeForTaxInspection());
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

    @Test(expected = OptimisticLockingFailureException.class)
    public void testOptimisticLocking() {
        TaxRecord record = new TaxRecord();
        record.setUahRevenue(100d);
        TaxRecord savedRecord = taxRepository.save(record);
        TaxRecord foundRecord = taxRepository.findOne(savedRecord.getId());

        savedRecord.setUsdRevenue(200d);

        taxRepository.save(savedRecord);
        taxRepository.save(foundRecord);
    }
}
