package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.entity.Employee;
import com.provectus.taxmanagement.service.TaxService;
import org.springframework.stereotype.Service;

/**
 * Created by alexey on 10.03.17.
 */
@Service
public class TaxServiceImpl implements TaxService {
    @Override
    public Double calcTaxSum(Employee employee, String period) {
        return null;
    }

    @Override
    public Double calcSalarySum(Employee employee, String period) {
        return null;
    }
}
