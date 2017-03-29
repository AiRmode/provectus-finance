package com.provectus.taxmanagement.controller;

import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.repository.TaxRepository;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

/**
 * Created by alexey on 28.03.17.
 */
@RestController
@RequestMapping(value = "/taxRecord")
public class TaxRecordController {
    private TaxRepository taxRepository;

    @RequestMapping(value = "/{quarterId}", method = RequestMethod.POST)
    public TaxRecord create(@RequestBody TaxRecord taxRecord, @PathVariable String quarterId) {
        taxRecord.setId(new ObjectId(quarterId));
        return taxRepository.save(taxRecord);
    }
}
