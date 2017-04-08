package com.provectus.taxmanagement.integration.repository;

import com.provectus.taxmanagement.entity.Employee;
import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.integration.TestParent;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by alexey on 11.03.17.
 */
public class EmployeeRepositoryTest extends TestParent {

    @Test
    public void testSaveEmployee() {
        Employee employee = new Employee();
        ObjectId objectId = ObjectId.get();
        employee.setId(objectId.toString());
        employee.setFirstName("Alex1");
        employee.setLastName("Ivanov");
        employee.setSecondName("Ivanovich");

        Employee record = employeeRepository.save(employee);
        assertNotNull(record);

        Employee one = employeeRepository.findOne(objectId);
        assertEquals(one, record);
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = new Employee();
        ObjectId objectId = ObjectId.get();
        employee.setId(objectId.toString());
        employee.setFirstName("Alex2");
        employee.setLastName("Ivanov");
        employee.setSecondName("Ivanovich");
        Employee record = employeeRepository.save(employee);
        assertNotNull(record);
        Employee one = employeeRepository.findOne(objectId);
        assertEquals(one, record);

        record.setLastName("Ivanov2");
        Employee updatedEmployee = employeeRepository.save(record);

        Employee updatedFromDB = employeeRepository.findOne(new ObjectId(updatedEmployee.getId()));
        assertEquals(record, updatedFromDB);
        assertEquals(updatedFromDB.getVersion(), new Long(1));
    }

    @Test
    public void testSaveDefaultIdEmppoyee() {
        Employee employee = new Employee();
        employee.setFirstName("Alex");
        employee.setLastName("Ivanov");
        employee.setSecondName("Ivanovich");

        Employee record = employeeRepository.save(employee);
        assertNotNull(record);
    }

    @Test
    public void testSaveEmployeeWithQuarterAndTax() {
        Employee employee = new Employee();
        employee.setFirstName("Alex");
        employee.setLastName("Ivanov");
        employee.setSecondName("Ivanovich");

        Quarter quarter = new Quarter();
        quarter.setQuarterDefinition(new Quarter.QuarterDefinition("Q4", 2016));

        TaxRecord taxRecord = new TaxRecord();
        taxRecord.setUsdRevenue(100d);
        taxRecord.setUahRevenue(50d);
        taxRecord.setExchRateUsdUahNBUatReceivingDate(5d);
        taxRecord.calculateVolumeForTaxInspection();
        taxRecord.calculateTaxValue();

        TaxRecord savedTaxRecord = taxRecordRepository.save(taxRecord);
        quarter.addTaxRecord(savedTaxRecord);
        Quarter savedQaurterRecord = quarterRepository.save(quarter);
        employee.addQuarter(savedQaurterRecord);
        Employee savedEmployeeRecord = employeeRepository.save(employee);

        Employee foundRecord = employeeRepository.findOne(new ObjectId(savedEmployeeRecord.getId()));
        Set<Quarter> quartersSet = foundRecord.getQuartersSet();
        assertFalse(quartersSet.isEmpty());
        assertFalse(new ArrayList<>(quartersSet).get(0).getTaxRecords().isEmpty());
        assertNotNull(savedEmployeeRecord);
    }

    @Test(expected = OptimisticLockingFailureException.class)
    public void testOptimisticLocking() {
        Employee employee = new Employee();
        employee.setFirstName("Vasya");

        Employee savedRecord = employeeRepository.save(employee);
        Employee foundRecord = employeeRepository.findOne(new ObjectId(savedRecord.getId()));

        savedRecord.setLastName("Ivanov");
        foundRecord.setLastName("Petrov");

        employeeRepository.save(savedRecord);
        employeeRepository.save(foundRecord);
    }
}
