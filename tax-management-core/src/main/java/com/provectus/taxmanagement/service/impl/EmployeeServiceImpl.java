package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.entity.Employee;
import com.provectus.taxmanagement.repository.EmployeeRepository;
import com.provectus.taxmanagement.repository.QuarterRepository;
import com.provectus.taxmanagement.repository.TaxRecordRepository;
import com.provectus.taxmanagement.service.EmployeeService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by alexey on 10.03.17.
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TaxRecordRepository taxRecordRepository;

    @Autowired
    private QuarterRepository quarterRepository;

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee, String id) {
        Employee one = employeeRepository.findOne(new ObjectId(id));
        one.setFirstName(employee.getFirstName());
        one.setLastName(employee.getLastName());
        one.setSecondName(employee.getSecondName());
        one.setComment(employee.getComment());
        one.setDepartment(employee.getDepartment());
        one.setEmail(employee.getEmail());
        one.setKved(employee.getKved());
        one.setQuartersSet(employee.getQuartersSet());
        one.setTaxPercentage(employee.getTaxPercentage());
        employeeRepository.save(one);
        return one;
    }
}
