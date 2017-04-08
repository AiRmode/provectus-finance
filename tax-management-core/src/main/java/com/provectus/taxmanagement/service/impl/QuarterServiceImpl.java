package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.entity.Employee;
import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.repository.EmployeeRepository;
import com.provectus.taxmanagement.repository.QuarterRepository;
import com.provectus.taxmanagement.service.EmployeeService;
import com.provectus.taxmanagement.service.QuarterService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by alexey on 05.04.17.
 */
@Service
public class QuarterServiceImpl implements QuarterService {

    @Autowired
    @Qualifier("employeeRepository")
    private EmployeeRepository employeeRepository;

    @Autowired
    @Qualifier("employeeService")
    private EmployeeService employeeService;

    @Autowired
    @Qualifier("quarterRepository")
    private QuarterRepository quarterRepository;

    @Override
    public Quarter addQuarter(String employeeId, Quarter quarter) {
        Employee employee = employeeRepository.findOne(new ObjectId(employeeId));
        employee.addQuarter(quarter);
        employeeService.save(employee);
        return quarter;
    }

    @Override
    public String delete(String employeeId, String quarterId) {
        Employee one = employeeRepository.findOne(new ObjectId(employeeId));
        one.removeQuarter(quarterRepository.findOne(new ObjectId(quarterId)));
        employeeService.save(one);
        return "deleted";
    }
}
