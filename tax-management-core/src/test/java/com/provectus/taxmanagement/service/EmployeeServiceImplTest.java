package com.provectus.taxmanagement.service;

import com.provectus.taxmanagement.entity.Employee;
import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.enums.QuarterName;
import com.provectus.taxmanagement.integration.TestParent;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by alexey on 12.03.17.
 */
public class EmployeeServiceImplTest extends TestParent {

    @Test
    public void testService() {
        Employee employee = new Employee();
        employee.setFirstName("Sanya");
        employee.setLastName("Petrov");
        employee.setSecondName("Nikolaevich");
        employeeRepository.save(employee);

        Quarter quarter = new Quarter();
        Quarter.QuarterDefinition definition = new Quarter.QuarterDefinition();
        definition.setQuarterName(QuarterName.Q1);
        definition.setYear(2017);
        quarter.setQuarterDefinition(definition);

        TaxRecord taxRecord = new TaxRecord();
        taxRecord.setUsdRevenue(100d);
        taxRecord.setUahRevenue(50d);
        taxRecord.setExchRateUsdUahNBUatReceivingDate(5d);

        quarter.addTaxRecord(taxRecord);

        Employee saved = employeeService.save(employee);
        quarterService.addQuarter(employee.getId(), quarter);
        assertNotNull(saved);

        Employee found = employeeRepository.findOne(new ObjectId(saved.getId()));
        assertNotNull(found);
        Set<Quarter> quartersSet = found.getQuartersSet();
        assertFalse(quartersSet.isEmpty());
        assertFalse(new ArrayList<>(quartersSet).get(0).getTaxRecords().isEmpty());
    }
}
