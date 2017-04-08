package com.provectus.taxmanagement.service;

import com.provectus.taxmanagement.entity.TaxRecord;

/**
 * Created by alexey on 06.04.17.
 */
public interface TaxRecordService {
    TaxRecord addTaxRecord(String employeeId, String quarterId, TaxRecord taxRecord);

    TaxRecord updateTaxRecord(String employeeId, String quarterId, TaxRecord taxRecord);

    void delete(String employeeId, String quarterId, String taxRecordId);
}
