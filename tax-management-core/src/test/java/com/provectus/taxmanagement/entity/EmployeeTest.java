package com.provectus.taxmanagement.entity;

import com.provectus.taxmanagement.enums.QuarterName;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by alexey on 13.03.17.
 */
public class EmployeeTest {

    /**
     * the Quarters order has to be by QuarterDefinition-DESC
     */
    @Test
    public void quartersOrderTest() {
        Employee employee = createEmployee();
        employee.addQuarter(new Quarter(new Quarter.QuarterDefinition("q2", 2016)));
        employee.addQuarter(new Quarter(new Quarter.QuarterDefinition("q4", 2016)));
        employee.addQuarter(new Quarter(new Quarter.QuarterDefinition("q1", 2016)));
        employee.addQuarter(new Quarter(new Quarter.QuarterDefinition("q3", 2016)));

        employee.addQuarter(new Quarter(new Quarter.QuarterDefinition("q4", 2011)));
        employee.addQuarter(new Quarter(new Quarter.QuarterDefinition("q3", 2011)));

        employee.addQuarter(new Quarter(new Quarter.QuarterDefinition("q2", 2017)));
        employee.addQuarter(new Quarter(new Quarter.QuarterDefinition("q1", 2017)));

        List<String> testList = new ArrayList<>();
        testList.add("q2 2017");
        testList.add("q1 2017");

        testList.add("q4 2016");
        testList.add("q3 2016");
        testList.add("q2 2016");
        testList.add("q1 2016");

        testList.add("q4 2011");
        testList.add("q3 2011");

        Set<Quarter> quartersSet = employee.getQuartersSet();
        int testDataCounter = 0;
        for (Quarter q : quartersSet) {
            String[] testValues = testList.get(testDataCounter).split(" ");
            assertTrue(q.getQuarterDefinition().getQuarterName().equals(QuarterName.valueOf(testValues[0].toUpperCase())));
            assertTrue(q.getQuarterDefinition().getYear().equals(Integer.valueOf(testValues[1])));
            testDataCounter++;
        }
    }

    @Test
    public void getQuarterByDefinitionTest() {
        Employee employee = createEmployee();

        Optional<Quarter> quarterByDefinition = employee.getQuarterByDefinition(new Quarter.QuarterDefinition("q3", 2016));
        assertTrue(quarterByDefinition.isPresent());

        quarterByDefinition = employee.getQuarterByDefinition(new Quarter.QuarterDefinition("Q4", 2016));
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
        Quarter quarter = new Quarter(new Quarter.QuarterDefinition("q3", 2016));
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

    @Test
    public void addTaxRecordToQuarterTest() {
        Employee employee = createEmployee();
        Set<Quarter> quartersSet = employee.getQuartersSet();
        String id = "58e54d6cca8b0bf0af1a3214";

        //set id
        quartersSet.stream().findFirst().get().setId(id);

        TaxRecord taxRecord = new TaxRecord();
        taxRecord.setUsdRevenue(10d);

        employee.addTaxRecordToQuarter(id, taxRecord);

        Quarter quarterById = employee.getQuarterById(id);
        List<TaxRecord> taxRecords = quarterById.getTaxRecords();
        assertFalse(taxRecords.isEmpty());
        assertEquals(taxRecords.get(0).getUahRevenue(), new Double(100));
        assertEquals(taxRecords.get(1).getUsdRevenue(), new Double(10));
    }

}
