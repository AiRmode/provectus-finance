package com.provectus.taxmanagement.integration;

import com.provectus.taxmanagement.configuration.TestTaxManagementConfiguration;
import com.provectus.taxmanagement.repository.EmployeeRepository;
import com.provectus.taxmanagement.repository.ExchangeRatesUahRepository;
import com.provectus.taxmanagement.repository.QuarterRepository;
import com.provectus.taxmanagement.repository.TaxRecordRepository;
import com.provectus.taxmanagement.service.EmployeeService;
import com.provectus.taxmanagement.service.ExchangeRateService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by alexey on 11.03.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestTaxManagementConfiguration.class)
public abstract class TestParent {

    @Autowired
    @Qualifier("employeeRepository")
    protected EmployeeRepository employeeRepository;

    @Autowired
    @Qualifier("taxRecordRepository")
    protected TaxRecordRepository taxRecordRepository;

    @Autowired
    @Qualifier("quarterRepository")
    protected QuarterRepository quarterRepository;

    @Autowired
    protected EmployeeService employeeService;

    @Autowired
    @Qualifier("exchangeRateUahService")
    protected ExchangeRateService exchangeRateService;

    @Autowired
    @Qualifier("exchangeRatesUahRepository")
    protected ExchangeRatesUahRepository exchangeRatesUahRepository;
}
