package com.provectus.taxmanagement.controller;

import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.repository.QuarterRepository;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

/**
 * Created by alexey on 28.03.17.
 */
@RestController
@RequestMapping(value = "/quarter")
public class QuarterController {

    private QuarterRepository quarterRepository;

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.POST)
    public Quarter create(@RequestBody Quarter quarter, @PathVariable String employeeId) {
        quarter.setId(new ObjectId(employeeId));
        return quarterRepository.save(quarter);
    }
}
