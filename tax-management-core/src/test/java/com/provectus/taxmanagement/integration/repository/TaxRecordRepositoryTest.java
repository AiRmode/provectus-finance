package com.provectus.taxmanagement.integration.repository;

import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.integration.TestParent;
import org.bson.types.ObjectId;
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

        TaxRecord savedRecord = taxRecordRepository.save(taxRecord);
        assertNotNull(savedRecord);

        TaxRecord foundRecord = taxRecordRepository.findOne(new ObjectId(savedRecord.getId()));
        assertEquals(taxRecord.getUsdRevenue(), foundRecord.getUsdRevenue());
        assertEquals(taxRecord.getExchRateUsdUahNBUatReceivingDate(), foundRecord.getExchRateUsdUahNBUatReceivingDate());
        assertEquals(taxRecord.getUahRevenue(), foundRecord.getUahRevenue());
    }

    @Test
    public void testDelete() {
        TaxRecord taxRecord = new TaxRecord();
        taxRecord.setUsdRevenue(1d);
        taxRecord.setExchRateUsdUahNBUatReceivingDate(10d);
        taxRecord.setUahRevenue(20d);

        TaxRecord savedRecord = taxRecordRepository.save(taxRecord);
        TaxRecord foundRecord = taxRecordRepository.findOne(new ObjectId(savedRecord.getId()));

        taxRecordRepository.delete(foundRecord);
        TaxRecord deletedRecord = taxRecordRepository.findOne(new ObjectId(foundRecord.getId()));
        assertNull(deletedRecord);
    }

    @Test(expected = OptimisticLockingFailureException.class)
    public void testOptimisticLocking() {
        TaxRecord record = new TaxRecord();
        record.setUahRevenue(100d);
        TaxRecord savedRecord = taxRecordRepository.save(record);
        TaxRecord foundRecord = taxRecordRepository.findOne(new ObjectId(savedRecord.getId()));

        savedRecord.setUsdRevenue(200d);

        taxRecordRepository.save(savedRecord);
        taxRecordRepository.save(foundRecord);
    }
}
