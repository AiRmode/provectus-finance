package com.provectus.taxmanagement.entity;

import com.provectus.taxmanagement.enums.QuarterName;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by alexey on 12.03.17.
 */
public class QuarterTest {
    @Test
    public void testQuarterCalculations() {
        Quarter quarter = new Quarter();
        Quarter.QuarterDefinition quarterDefinition = new Quarter.QuarterDefinition();
        quarterDefinition.setYear(2016);
        quarterDefinition.setQuarterName(QuarterName.Q2);
        quarter.setQuarterDefinition(quarterDefinition);

        TaxRecord taxRecord = new TaxRecord();
        taxRecord.setUahRevenue(100d);
        taxRecord.calculateVolumeForTaxInspection();
        taxRecord.calculateTaxValue();

        quarter.addTaxRecord(taxRecord);

        quarter.calculateUahVolumeForTaxes();
        quarter.calculateTaxVolume();

        Double taxVolume = quarter.getTaxVolume();
        assertNotNull(taxVolume);
        assertEquals(taxVolume, new Double(5));

        Double uahVolumeForTaxes = quarter.getUahVolumeForTaxes();
        assertNotNull(uahVolumeForTaxes);
        assertEquals(uahVolumeForTaxes, new Double(100));
    }

    @Test
    public void testFindByQuarters() {
        Quarter quarter = new Quarter();
        Quarter.QuarterDefinition quarterDefinition = new Quarter.QuarterDefinition(QuarterName.Q3, 2016);
        quarter.setQuarterDefinition(quarterDefinition);

        Employee employee = new Employee();
        employee.addQuarter(quarter);

        Optional<Quarter> quarterByTitle = employee.getQuarterByDefinition(quarterDefinition);
        assertNotNull(quarterByTitle.get());
    }
}
