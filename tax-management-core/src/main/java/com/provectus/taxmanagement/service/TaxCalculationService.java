package com.provectus.taxmanagement.service;

import com.provectus.taxmanagement.entity.Employee;
import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.entity.TaxRecord;

/**
 * Created by alexey on 10.03.17.
 */
public interface TaxCalculationService {
    Double calculateTaxValue(Employee employee, Quarter.QuarterDefinition definition);

    Double calculateTaxation(Employee employee, Quarter.QuarterDefinition definition);

    Double calculateTaxValue(TaxRecord taxRecord);

    Double calculateTaxation(TaxRecord taxRecord);

    Double calculateTaxValue(Quarter quarter);

    Double calculateTaxation(Quarter quarter);

    Double getTotalTaxationByYear(String employeeId, Integer year);

    Double getTotalTaxesVolumeByYear(String employeeId, Integer year);

}
