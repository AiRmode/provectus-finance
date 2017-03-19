package com.provectus.taxmanagement.controller;

import com.provectus.taxmanagement.entity.Employee;
import com.provectus.taxmanagement.repository.EmployeeRepository;
import com.provectus.taxmanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by alexey on 14.03.17.
 */
@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * get employee by fist name, second name or last name
     * method GET
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/search/{name}", method = RequestMethod.GET)
    public List<Employee> findByAnyName(@PathVariable String name) {
        List<Employee> foundRecords = employeeRepository.findByFirstNameLikeIgnoreCase(name);
        foundRecords.addAll(employeeRepository.findByLastNameLikeIgnoreCase(name));
        foundRecords.addAll(employeeRepository.findBySecondNameLikeIgnoreCase(name));

        HashSet<Employee> uniqEmployees = new HashSet<>();
        uniqEmployees.addAll(foundRecords);
        return new ArrayList<>(uniqEmployees);
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
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Employee create(@RequestBody Employee employee) {
        return employeeService.save(employee);
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
