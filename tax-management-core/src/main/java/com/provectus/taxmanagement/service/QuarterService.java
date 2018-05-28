package com.provectus.taxmanagement.service;

import com.provectus.taxmanagement.entity.Employee;
import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.entity.TaxRecord;

import java.util.Optional;
import java.util.Set;

/**
 * Created by alexey on 05.04.17.
 */
public interface QuarterService {
    Quarter addQuarter(String employeeId, Quarter quarter);

    String delete(String employeeId, String quarterId);

    Set<Quarter> getAllQuartersByYear(String employeeId, Integer year);

    Optional<Quarter> getQuarterByDefinition(Employee employee, Quarter.QuarterDefinition quarterTitle);

    Quarter getQuarterById(String employeeId, String quarterId);

    Quarter addTaxRecordToQuarter(Quarter quarter, TaxRecord taxRecord);

    boolean removeTaxRecord(Quarter quarter, TaxRecord taxRecord);

    TaxRecord getTaxRecord(Quarter quarter, String id);

    boolean removeTaxRecordById(Quarter quarter, String id);

    boolean removeTaxRecordById(String quarterId, String id);
}
