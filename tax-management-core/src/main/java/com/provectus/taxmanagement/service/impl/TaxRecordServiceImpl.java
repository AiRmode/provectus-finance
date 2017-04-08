package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.entity.Employee;
import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.repository.EmployeeRepository;
import com.provectus.taxmanagement.service.EmployeeService;
import com.provectus.taxmanagement.service.TaxRecordService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by alexey on 06.04.17.
 */
@Service
public class TaxRecordServiceImpl implements TaxRecordService {

    @Autowired
    @Qualifier("employeeService")
    private EmployeeService employeeService;

    @Autowired
    @Qualifier("employeeRepository")
    private EmployeeRepository employeeRepository;

    @Override
    public TaxRecord addTaxRecord(String employeeId, String quarterId, TaxRecord taxRecord) {
        Employee one = employeeRepository.findOne(new ObjectId(employeeId));
        one.addTaxRecordToQuarter(quarterId, taxRecord);
        employeeService.save(one);
        return taxRecord;
    }

    @Override
    public TaxRecord updateTaxRecord(String employeeId, String quarterId, TaxRecord taxRecord) {
        Employee one = employeeRepository.findOne(new ObjectId(employeeId));
        Quarter quarterById = one.getQuarterById(quarterId);
        TaxRecord tax = quarterById.getTaxRecord(taxRecord.getId());
        tax.setTaxPercentage(taxRecord.getTaxPercentage());
        tax.setCounterpartyName(taxRecord.getCounterpartyName());
        tax.setReceivingDate(taxRecord.getReceivingDate());
        tax.setUahRevenue(taxRecord.getUahRevenue());
        tax.setUsdRevenue(taxRecord.getUsdRevenue());
        employeeService.save(one);
        return tax;
    }

    @Override
    public void delete(String employeeId, String quarterId, String taxRecordId) {
        Employee one = employeeRepository.findOne(new ObjectId(employeeId));
        Quarter quarterById = one.getQuarterById(quarterId);
    }
}
