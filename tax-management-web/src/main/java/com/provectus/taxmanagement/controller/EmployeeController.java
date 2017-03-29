package com.provectus.taxmanagement.controller;

import com.provectus.taxmanagement.entity.Employee;
import com.provectus.taxmanagement.repository.EmployeeRepository;
import com.provectus.taxmanagement.service.EmployeeService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @CrossOrigin
    @RequestMapping(value = "/search/{name}", method = RequestMethod.GET)
    public Set<Employee> findByAnyName(@PathVariable String name) {
        List<Employee> foundRecords = employeeRepository.findByFirstNameLikeIgnoreCase(name);
        foundRecords.addAll(employeeRepository.findByLastNameLikeIgnoreCase(name));
        foundRecords.addAll(employeeRepository.findBySecondNameLikeIgnoreCase(name));

        HashSet<Employee> uniqEmployees = new HashSet<>();
        uniqEmployees.addAll(foundRecords);

        return uniqEmployees;
    }

    @CrossOrigin
    @RequestMapping(value = "/employees", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public HttpEntity<PagedResources<Resource<Employee>>> getPage(Pageable pageable, PagedResourcesAssembler<Employee> assembler) {
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return new ResponseEntity<>(assembler.toResource(employeePage), HttpStatus.OK);
    }

    /**
     * method GET
     *
     * @param id
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Employee findById(@PathVariable String id) {
        return employeeRepository.findOne(new ObjectId(id));
    }

    /**
     * method POST
     *
     * @param employee json object from client
     * @return
     */
    @CrossOrigin
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
    public boolean update(String id) {
        return false;
    }

    /**
     * @param id
     */
    public void delete(String id) {

    }
}
