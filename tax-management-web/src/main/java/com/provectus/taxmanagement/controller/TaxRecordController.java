package com.provectus.taxmanagement.controller;

import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.service.TaxRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * Created by alexey on 28.03.17.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/taxRecord")
public class TaxRecordController {

    @Autowired
    @Qualifier("taxRecordService")
    private TaxRecordService taxRecordService;

    /**
     * @param taxRecord
     * @param employeeId
     * @param quarterId
     * @return
     */
    @RequestMapping(value = "/{employeeId}/{quarterId}", method = RequestMethod.POST)
    public TaxRecord create(@RequestBody TaxRecord taxRecord, @PathVariable String employeeId, @PathVariable String quarterId) {
        taxRecordService.addTaxRecord(employeeId, quarterId, taxRecord);
        return taxRecord;
    }

    /**
     *
     * @param taxRecord
     * @param employeeId
     * @param quarterId
     * @return
     */
    @RequestMapping(value = "/{employeeId}/{quarterId}", method = RequestMethod.PUT)
    public TaxRecord update(@RequestBody TaxRecord taxRecord, @PathVariable String employeeId, @PathVariable String quarterId) {
        taxRecordService.updateTaxRecord(employeeId, quarterId, taxRecord);
        return taxRecord;
    }

    /**
     *
     * @param employeeId
     * @param quarterId
     * @param taxRecordId
     * @return
     */
    @RequestMapping(value = "/{employeeId}/{quarterId}/{taxRecordId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String employeeId, @PathVariable String quarterId, @PathVariable String taxRecordId) {
        taxRecordService.delete(employeeId, quarterId, taxRecordId);
        return "deleted";
    }

}
