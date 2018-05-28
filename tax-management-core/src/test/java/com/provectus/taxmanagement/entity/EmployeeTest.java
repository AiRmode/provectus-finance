package com.provectus.taxmanagement.entity;

import com.provectus.taxmanagement.enums.QuarterName;
import com.provectus.taxmanagement.integration.TestParent;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by alexey on 13.03.17.
 */
public class EmployeeTest extends TestParent {

    /**
     * the Quarters order has to be by QuarterDefinition-DESC
     */
    @Test
    public void quartersOrderTest() {
        Employee employee = createEmployee();
        quarterService.addQuarter(employee.getId(), new Quarter(new Quarter.QuarterDefinition("q2", 2016)));
        quarterService.addQuarter(employee.getId(), new Quarter(new Quarter.QuarterDefinition("q4", 2016)));
        quarterService.addQuarter(employee.getId(), new Quarter(new Quarter.QuarterDefinition("q1", 2016)));
        quarterService.addQuarter(employee.getId(), new Quarter(new Quarter.QuarterDefinition("q3", 2016)));

        quarterService.addQuarter(employee.getId(), new Quarter(new Quarter.QuarterDefinition("q4", 2011)));
        quarterService.addQuarter(employee.getId(), new Quarter(new Quarter.QuarterDefinition("q3", 2011)));

        quarterService.addQuarter(employee.getId(), new Quarter(new Quarter.QuarterDefinition("q2", 2017)));
        quarterService.addQuarter(employee.getId(), new Quarter(new Quarter.QuarterDefinition("q1", 2017)));

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

        Optional<Quarter> quarterByDefinition = quarterService.getQuarterByDefinition(employee, new Quarter.QuarterDefinition("q3", 2016));
        assertTrue(quarterByDefinition.isPresent());

        quarterByDefinition = quarterService.getQuarterByDefinition(employee, new Quarter.QuarterDefinition("Q4", 2016));
        assertFalse(quarterByDefinition.isPresent());

        employee = employeeRepository.findOne(new ObjectId(employee.getId()));
        assertTrue(employee.getQuartersSet().size() == 1);

        Set<Quarter> allQuartersByYear = quarterService.getAllQuartersByYear(employee.getId(), 2016);
        assertTrue(allQuartersByYear.size() == 1);

        Double totalUahVolumeForTaxesByYear = taxCalculationService.getTotalTaxationByYear(employee.getId(), 2016);
        assertEquals(totalUahVolumeForTaxesByYear, new Double(100));

        Double totalTaxesVolumeByYear = taxCalculationService.getTotalTaxesVolumeByYear(employee.getId(), 2016);
        assertEquals(totalTaxesVolumeByYear, new Double(5));
    }

    private Employee createEmployee() {
        Employee employee = new Employee();
        employeeRepository.save(employee);

        Quarter quarter = new Quarter(new Quarter.QuarterDefinition("q3", 2016));
        Quarter savedQuarter = quarterRepository.save(quarter);

        TaxRecord taxRecord = new TaxRecord();
        taxRecord.setUahRevenue(100d);
        TaxRecord saved = taxRecordRepository.save(taxRecord);
        Quarter updatedQuarter = quarterService.addTaxRecordToQuarter(savedQuarter, saved);

        quarterService.addQuarter(employee.getId(), updatedQuarter);

        return employee;
    }

    @Test
    public void addTaxRecordToQuarterTest() {
        Employee employee = createEmployee();
        Set<Quarter> quartersSet = employeeRepository.findOne(new ObjectId(employee.getId())).getQuartersSet();

        //set id
        Quarter quarter = quartersSet.stream().findFirst().get();

        TaxRecord taxRecord = new TaxRecord();
        taxRecord.setUsdRevenue(10d);
        TaxRecord saved = taxRecordRepository.save(taxRecord);

        quarterService.addTaxRecordToQuarter(quarter, saved);

        Quarter quarterById = quarterService.getQuarterById(employee.getId(), quarter.getId());
        List<TaxRecord> taxRecords = quarterById.getTaxRecords();
        assertFalse(taxRecords.isEmpty());
        assertEquals(taxRecords.get(0).getUahRevenue(), new Double(100));
        assertEquals(taxRecords.get(1).getUsdRevenue(), new Double(10));
    }

}
