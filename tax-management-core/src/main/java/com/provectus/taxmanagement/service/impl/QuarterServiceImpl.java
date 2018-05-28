package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.entity.Employee;
import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.repository.EmployeeRepository;
import com.provectus.taxmanagement.repository.QuarterRepository;
import com.provectus.taxmanagement.service.EmployeeService;
import com.provectus.taxmanagement.service.QuarterService;
import com.provectus.taxmanagement.service.TaxRecordService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by alexey on 05.04.17.
 */
@Service("quarterService")
public class QuarterServiceImpl implements QuarterService {

    @Autowired
    @Qualifier("employeeService")
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private QuarterRepository quarterRepository;

    @Autowired
    private TaxRecordService taxRecordService;

    @Override
    public Quarter addQuarter(String employeeId, Quarter quarter) {
        Employee employee = employeeRepository.findOne(new ObjectId(employeeId));
        //calculate only after added to employee, as a quarter could be added to existing one
        Optional<Quarter> existingQuarter = getQuarterByDefinition(employee, quarter.getQuarterDefinition());

        if (existingQuarter.isPresent()) {
            List<TaxRecord> taxRecords = quarter.getTaxRecords();
            taxRecords.forEach(taxRecord -> taxRecordService.save(taxRecord));
            existingQuarter.get().addTaxRecords(taxRecords);
            quarterRepository.save(existingQuarter.get());
            return existingQuarter.get();
        } else {
            boolean add = employee.getQuartersSet().add(quarter);
            List<TaxRecord> taxRecords = quarter.getTaxRecords();
            taxRecords.forEach(taxRecord -> taxRecordService.save(taxRecord));
            quarterRepository.save(quarter);
            employeeService.save(employee);
            return quarter;
        }
    }

    @Override
    public String delete(String employeeId, String quarterId) {
        Employee employee = employeeRepository.findOne(new ObjectId(employeeId));
        Quarter quarter = quarterRepository.findOne(new ObjectId(quarterId));

        quarterRepository.delete(quarter);
        removeQuarter(employee, quarter);
        employeeService.save(employee);

        return "deleted";
    }

    @Override
    public Set<Quarter> getAllQuartersByYear(String employeeId, Integer year) {
        Employee employee = employeeRepository.findOne(new ObjectId(employeeId));
        Set<Quarter> quarters = new HashSet<>();
        for (Quarter quarter : employee.getQuartersSet()) {
            if (quarter.getQuarterDefinition().getYear().equals(year)) {
                quarters.add(quarter);
            }
        }
        return quarters;
    }

    @Override
    public Optional<Quarter> getQuarterByDefinition(Employee employee, Quarter.QuarterDefinition definition) {
        Employee one = employeeRepository.findOne(new ObjectId(employee.getId()));
        Optional<Quarter> first = one.getQuartersSet().stream().filter(quarter -> quarter.getQuarterDefinition().equals(definition)).findFirst();
        return first;
    }

    @Override
    public Quarter getQuarterById(String employeeId, String quarterId) {
        Employee employee = employeeRepository.findOne(new ObjectId(employeeId));
        Optional<Quarter> existingQuarter = employee.getQuartersSet().stream().filter(q -> q.getId().equals(quarterId)).findFirst();
        return existingQuarter.orElse(null);
    }

    @Override
    public Quarter addTaxRecordToQuarter(Quarter quarter, TaxRecord taxRecord) {
        Quarter foundQuarter;
        if (quarter.getId() == null || (foundQuarter = quarterRepository.findOne(new ObjectId(quarter.getId()))) == null) {
            quarter.addTaxRecord(taxRecord);
            quarterRepository.save(quarter);
            return quarter;
        } else {
            foundQuarter.addTaxRecord(taxRecord);
            quarterRepository.save(foundQuarter);
            return foundQuarter;
        }
    }

    private boolean removeQuarter(Employee employee, Quarter quarter) {
        return employee.getQuartersSet().remove(quarter);
    }

    @Override
    public boolean removeTaxRecord(Quarter quarter, TaxRecord taxRecord) {
        return quarter.getTaxRecords().remove(taxRecord);
    }

    @Override
    public TaxRecord getTaxRecord(Quarter quarter, String id) {
        for (TaxRecord taxRecord : quarter.getTaxRecords()) {
            if (taxRecord.getId().equals(id)) {
                return taxRecord;
            }
        }
        return null;
    }

    @Override
    public boolean removeTaxRecordById(Quarter quarter, String id) {
        boolean b = quarter.getTaxRecords().removeIf(next -> next.getId().equals(id));
        quarterRepository.save(quarter);
        return b;
    }

    @Override
    public boolean removeTaxRecordById(String quarterId, String id) {
        Quarter quarter = quarterRepository.findOne(new ObjectId(quarterId));
        return removeTaxRecordById(quarter, id);
    }
}
