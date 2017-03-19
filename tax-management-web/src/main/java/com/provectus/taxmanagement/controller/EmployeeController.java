package com.provectus.taxmanagement.controller;

import com.provectus.taxmanagement.entity.Employee;
import com.provectus.taxmanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey on 14.03.17.
 */
@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * get employee by fist name, second name or last name
     * method GET
     *
     * @param firstName
     * @param secondName
     * @param lastName
     * @return
     */
    public List<Employee> findByAnyName(String firstName, String secondName, String lastName) {
        return new ArrayList<>();
    }

    /**
     * method GET
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Employee findById(@PathVariable String id) {
        Employee employee = new Employee();
        employee.setFirstName("Vasya");
        employee.setSecondName("Ivanovich");
        employee.setComment("test comment");
        return employee;
    }

    /**
     * method POST
     *
     * @param employee
     * @return
     */
    public Employee create(Employee employee) {
        return employee;
    }

    /**
     * method PUT
     *
     * @param id
     * @return
     */
    public Employee update(String id) {
        return null;
    }

    /**
     * @param id
     */
    public void delete(String id) {

    }
}
