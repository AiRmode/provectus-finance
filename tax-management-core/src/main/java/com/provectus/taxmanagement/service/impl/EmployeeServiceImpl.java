package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.entity.Employee;
import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.repository.EmployeeRepository;
import com.provectus.taxmanagement.repository.QuarterRepository;
import com.provectus.taxmanagement.repository.TaxRepository;
import com.provectus.taxmanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by alexey on 10.03.17.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    @Qualifier("employeeRepository")
    private EmployeeRepository employeeRepository;

    @Autowired
    @Qualifier("taxRepository")
    private TaxRepository taxRepository;

    @Autowired
    @Qualifier("quarterRepository")
    private QuarterRepository quarterRepository;

    @Override
    public Employee save(Employee employee) {
        Set<Quarter> quartersSet = employee.getQuartersSet();
        for (Quarter quarter : quartersSet) {
            List<TaxRecord> taxRecords = quarter.getTaxRecords();
            for (TaxRecord taxRecord : taxRecords) {
                taxRecord = taxRepository.save(taxRecord);
            }
            quarter = quarterRepository.save(quarter);
        }
        return employeeRepository.save(employee);
    }
}
