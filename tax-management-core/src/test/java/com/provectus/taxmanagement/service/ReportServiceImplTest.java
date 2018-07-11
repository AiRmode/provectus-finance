package com.provectus.taxmanagement.service;

import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.enums.QuarterName;
import com.provectus.taxmanagement.integration.TestParent;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

public class ReportServiceImplTest extends TestParent {

    @Test
    @Ignore
    public void testCreateTaxReport() {
        Quarter.QuarterDefinition quarterDefinition = new Quarter.QuarterDefinition();
        quarterDefinition.setQuarterName(QuarterName.Q1);
        quarterDefinition.setYear(2018);

        Quarter quarter = new Quarter(quarterDefinition);

        TaxRecord taxRecord = new TaxRecord();
        taxRecord.setReceivingDate(Date.from(LocalDate.of(2018,1,1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        taxRecord.setUahRevenue(100d);
        taxRecord.setUsdRevenue(10d);
        taxRecord.setExchRateUsdUahNBUatReceivingDate(10d);

        quarter.addTaxRecord(taxRecord);

        Set<Quarter> set = new HashSet<>();
        set.add(quarter);

        File taxReport = reportService.generateTaxReport(set);
        assertNotNull(taxReport);
    }
}
