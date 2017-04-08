package com.provectus.taxmanagement.controller;

import com.provectus.taxmanagement.entity.Employee;
import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.repository.EmployeeRepository;
import com.provectus.taxmanagement.repository.QuarterRepository;
import com.provectus.taxmanagement.service.QuarterService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

/**
 * Created by alexey on 28.03.17.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/quarter")
public class QuarterController {

    @Autowired
    @Qualifier("quarterRepository")
    private QuarterRepository quarterRepository;

    @Autowired
    @Qualifier("employeeRepository")
    private EmployeeRepository employeeRepository;

    @Autowired
    @Qualifier("quarterService")
    private QuarterService quarterService;

    /**
     * @param quarter
     * @param employeeId
     * @return
     */
    @RequestMapping(value = "/{employeeId}", method = RequestMethod.POST)
    public Quarter create(@RequestBody Quarter quarter, @PathVariable String employeeId) {
        return quarterService.addQuarter(employeeId, quarter);
    }

    /**
     * @param quarterId
     * @return
     */
    @RequestMapping(value = "/{quarterId}/{employeeId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String employeeId, @PathVariable String quarterId) {
        quarterService.delete(employeeId, quarterId);
        return "deleted";
    }

    /**
     * method GET
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Quarter findById(@PathVariable String id) {
        return quarterRepository.findOne(new ObjectId(id));
    }

    /**
     * @param year
     * @param quarterName
     * @return
     */
    @RequestMapping(value = "/{employeeId}/{year}/{quarterName}", method = RequestMethod.GET)
    public Quarter findByQuarterDefinition(@PathVariable Integer year, @PathVariable String quarterName, @PathVariable String employeeId) {
        Employee one = employeeRepository.findOne(new ObjectId(employeeId));
        Quarter.QuarterDefinition quarterDefinition = new Quarter.QuarterDefinition(quarterName, year);
        Optional<Quarter> quarter = one.getQuarterByDefinition(quarterDefinition);
        return quarter.isPresent() ? quarter.get() : null;
    }

    /**
     * @param employeeId
     * @return
     */
    @RequestMapping(value = "/search/{employeeId}", method = RequestMethod.GET)
    public Set<Quarter> findByEmployeeId(@PathVariable String employeeId) {
        Employee one = employeeRepository.findOne(new ObjectId(employeeId));
        return one.getQuartersSet();
    }
}
