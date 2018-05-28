package com.provectus.taxmanagement.entity;

import com.provectus.taxmanagement.enums.QuarterName;
import com.provectus.taxmanagement.integration.TestParent;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by alexey on 12.03.17.
 */
public class QuarterTest extends TestParent {

    @Test
    public void testQuarterCalculations() {
        Quarter quarter = new Quarter();
        Quarter.QuarterDefinition quarterDefinition = new Quarter.QuarterDefinition();
        quarterDefinition.setYear(2016);
        quarterDefinition.setQuarterName(QuarterName.Q2);
        quarter.setQuarterDefinition(quarterDefinition);

        TaxRecord taxRecord = new TaxRecord();
        taxRecord.setUahRevenue(100d);
        Double taxation = taxCalculationService.calculateTaxation(taxRecord);
        Double taxValue = taxCalculationService.calculateTaxValue(taxRecord);

        quarter.addTaxRecord(taxRecord);

        Double quarterTaxation = taxCalculationService.calculateTaxation(quarter);
        Double quarterTaxValue = taxCalculationService.calculateTaxValue(quarter);

        assertNotNull(taxValue);
        assertEquals(taxValue, new Double(5));

        assertNotNull(taxation);
        assertEquals(taxation, new Double(100));

        assertEquals(taxValue, quarterTaxValue);
        assertEquals(taxation, quarterTaxation);
    }

    @Test
    public void testAddExistingQuarter() {
        int year = 2016;

        Quarter quarter = new Quarter();
        Quarter.QuarterDefinition quarterDefinition = new Quarter.QuarterDefinition();
        quarterDefinition.setYear(year);
        quarterDefinition.setQuarterName(QuarterName.Q2);
        quarter.setQuarterDefinition(quarterDefinition);
        quarterRepository.save(quarter);

        Quarter theSameQuarter = new Quarter();
        Quarter.QuarterDefinition theSameQuarterDefinition = new Quarter.QuarterDefinition();
        theSameQuarterDefinition.setYear(year);
        theSameQuarterDefinition.setQuarterName(QuarterName.Q2);
        theSameQuarter.setQuarterDefinition(quarterDefinition);
        quarterRepository.save(theSameQuarter);

        TaxRecord taxRecord = new TaxRecord();
        taxRecord.setUahRevenue(100d);
        taxCalculationService.calculateTaxation(taxRecord);
        taxCalculationService.calculateTaxValue(taxRecord);
        taxRecordRepository.save(taxRecord);

        Quarter existingQuarter = quarterService.addTaxRecordToQuarter(quarter, taxRecord);
        Double taxVolume = taxCalculationService.calculateTaxation(existingQuarter);
        Double uahVolumeForTaxes = taxCalculationService.calculateTaxValue(existingQuarter);

        Employee employee = new Employee();
        employeeRepository.save(employee);
        quarterService.addQuarter(employee.getId(), existingQuarter);
        Double totalTaxesVolumeByYear = taxCalculationService.getTotalTaxationByYear(employee.getId(), year);
        Double totalUahVolumeForTaxesByYear = taxCalculationService.getTotalTaxesVolumeByYear(employee.getId(), year);

        assertEquals(taxVolume, totalTaxesVolumeByYear);
        assertEquals(uahVolumeForTaxes, totalUahVolumeForTaxesByYear);

        TaxRecord taxRecord1 = new TaxRecord();
        taxRecord1.setUahRevenue(200d);
        taxCalculationService.calculateTaxation(taxRecord1);
        taxCalculationService.calculateTaxValue(taxRecord1);

        taxRecord1 = taxRecordRepository.save(taxRecord1);
        quarterService.addTaxRecordToQuarter(existingQuarter, taxRecord1);
        taxCalculationService.calculateTaxValue(theSameQuarter);
        taxCalculationService.calculateTaxation(theSameQuarter);

        quarterService.addQuarter(employee.getId(), theSameQuarter);
        Double totalTaxesVolumeByYear1 = taxCalculationService.getTotalTaxationByYear(employee.getId(), year);
        Double totalUahVolumeForTaxesByYear1 = taxCalculationService.getTotalTaxesVolumeByYear(employee.getId(), year);

        assertEquals((Double) (totalTaxesVolumeByYear * 3), totalTaxesVolumeByYear1);
        assertEquals((Double) (totalUahVolumeForTaxesByYear * 3), totalUahVolumeForTaxesByYear1);
    }

    @Test
    public void testFindByQuarters() {
        Quarter quarter = new Quarter();
        Quarter.QuarterDefinition quarterDefinition = new Quarter.QuarterDefinition("q3", 2016);
        quarter.setQuarterDefinition(quarterDefinition);

        Employee employee = new Employee();
        employeeRepository.save(employee);
        quarterService.addQuarter(employee.getId(), quarter);

        Optional<Quarter> quarterByTitle = quarterService.getQuarterByDefinition(employee, quarterDefinition);
        assertNotNull(quarterByTitle.get());
    }
}
