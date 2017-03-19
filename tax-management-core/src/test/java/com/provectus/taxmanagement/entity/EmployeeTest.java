package com.provectus.taxmanagement.entity;

import com.provectus.taxmanagement.enums.QuarterName;
import org.junit.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by alexey on 13.03.17.
 */
public class EmployeeTest {

    @Test
    public void getQuarterByDefinitionTest() {
        Employee employee = createEmployee();

        Optional<Quarter> quarterByDefinition = employee.getQuarterByDefinition(new Quarter.QuarterDefinition(QuarterName.Q3, 2016));
        assertTrue(quarterByDefinition.isPresent());

        quarterByDefinition = employee.getQuarterByDefinition(new Quarter.QuarterDefinition(QuarterName.Q4, 2016));
        assertFalse(quarterByDefinition.isPresent());

        assertTrue(employee.getQuartersSet().size() == 1);

        Set<Quarter> allQuartersByYear = employee.getAllQuartersByYear(2016);
        assertTrue(allQuartersByYear.size() == 1);

        Double totalUahVolumeForTaxesByYear = employee.getTotalUahVolumeForTaxesByYear(2016);
        assertEquals(totalUahVolumeForTaxesByYear, new Double(100));

        Double totalTaxesVolumeByYear = employee.getTotalTaxesVolumeByYear(2016);
        assertEquals(totalTaxesVolumeByYear, new Double(5));
    }

    private Employee createEmployee() {
        Employee employee = new Employee();
        Quarter quarter = new Quarter(new Quarter.QuarterDefinition(QuarterName.Q3, 2016));
        TaxRecord taxRecord = new TaxRecord();

        taxRecord.setUahRevenue(100d);
        taxRecord.calculateVolumeForTaxInspection();
        taxRecord.calculateTaxValue();

        quarter.addTaxRecord(taxRecord);

        quarter.calculateUahVolumeForTaxes();
        quarter.calculateTaxVolume();

        employee.addQuarter(quarter);

        return employee;
    }
}
