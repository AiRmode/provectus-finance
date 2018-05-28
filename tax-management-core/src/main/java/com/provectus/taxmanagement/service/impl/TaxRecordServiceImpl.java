package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.repository.EmployeeRepository;
import com.provectus.taxmanagement.repository.TaxRecordRepository;
import com.provectus.taxmanagement.service.EmployeeService;
import com.provectus.taxmanagement.service.QuarterService;
import com.provectus.taxmanagement.service.TaxRecordService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by alexey on 06.04.17.
 */
@Service("taxRecordService")
public class TaxRecordServiceImpl implements TaxRecordService {

    @Autowired
    @Qualifier("employeeService")
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    @Qualifier("taxRecordRepository")
    private TaxRecordRepository taxRecordRepository;

    @Autowired
    private QuarterService quarterService;

    @Override
    public TaxRecord updateTaxRecord(String employeeId, String quarterId, TaxRecord taxRecord) {
        TaxRecord tax = taxRecordRepository.findOne(new ObjectId(taxRecord.getId()));
        tax.setTaxPercentage(taxRecord.getTaxPercentage());
        tax.setCounterpartyName(taxRecord.getCounterpartyName());
        tax.setReceivingDate(taxRecord.getReceivingDate());
        tax.setUahRevenue(taxRecord.getUahRevenue());
        tax.setUsdRevenue(taxRecord.getUsdRevenue());
        taxRecordRepository.save(tax);
        return tax;
    }

    @Override
    public void delete(String employeeId, String quarterId, String taxRecordId) {
        quarterService.removeTaxRecordById(quarterId, taxRecordId);
        taxRecordRepository.delete(new ObjectId(taxRecordId));
    }

    @Override
    public TaxRecord save(TaxRecord taxRecord) {
        return taxRecordRepository.save(taxRecord);
    }

    @Override
    public TaxRecord addTaxRecord(String employeeId, String quarterId, TaxRecord taxRecord) {
        Quarter quarterById = quarterService.getQuarterById(employeeId, quarterId);
        TaxRecord savedRecord = taxRecordRepository.save(taxRecord);
        Quarter quarter = quarterService.addTaxRecordToQuarter(quarterById, savedRecord);
        return savedRecord;
    }
}
