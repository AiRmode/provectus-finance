package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.entity.Employee;
import com.provectus.taxmanagement.service.TaxCalculationService;
import org.springframework.stereotype.Service;

/**
 * Created by alexey on 10.03.17.
 */
@Service("taxCalculationService")
public class TaxCalculationServiceImpl implements TaxCalculationService {
    @Override
    public Double calcTaxSum(Employee employee, String period) {
        return null;
    }

    @Override
    public Double calcSalarySum(Employee employee, String period) {
        return null;
    }
}
