package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.entity.Employee;
import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.service.QuarterService;
import com.provectus.taxmanagement.service.TaxCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by alexey on 10.03.17.
 */
@Service("taxCalculationService")
public class TaxCalculationServiceImpl implements TaxCalculationService {
    public static final int VALUE_NOT_SET = 0;

    @Autowired
    @Qualifier("quarterService")
    private QuarterService quarterService;

    @Override
    public Double calculateTaxValue(Employee employee, Quarter.QuarterDefinition definition) {
        Optional<Quarter> quarterBox = quarterService.getQuarterByDefinition(employee, definition);
        if (quarterBox.isPresent()) {
            return calculateTaxation(quarterBox.get());
        }
        return (double) VALUE_NOT_SET;
    }

    @Override
    public Double calculateTaxation(Employee employee, Quarter.QuarterDefinition definition) {
        Optional<Quarter> quarterBox = quarterService.getQuarterByDefinition(employee, definition);
        if (quarterBox.isPresent()) {
            return calculateTaxValue(quarterBox.get());
        }
        return (double) VALUE_NOT_SET;
    }

    @Override
    public Double calculateTaxValue(TaxRecord taxRecord) {
        return calculateTaxation(taxRecord) * taxRecord.getTaxPercentage() / 100d;
    }

    @Override
    public Double calculateTaxation(TaxRecord taxRecord) {
        return taxRecord.getUsdRevenue() * taxRecord.getExchRateUsdUahNBUatReceivingDate() + taxRecord.getUahRevenue();
    }

    @Override
    public Double calculateTaxValue(Quarter quarter) {
        Double taxVolume = (double) VALUE_NOT_SET;
        for (TaxRecord taxRecord : quarter.getTaxRecords()) {
            taxVolume += calculateTaxValue(taxRecord);
        }
        return taxVolume;
    }

    @Override
    public Double calculateTaxation(Quarter quarter) {
        Double uahVolumeForTaxes = (double) VALUE_NOT_SET;
        for (TaxRecord taxRecord : quarter.getTaxRecords()) {
            uahVolumeForTaxes += calculateTaxation(taxRecord);
        }
        return uahVolumeForTaxes;
    }

    @Override
    public Double getTotalTaxationByYear(String employeeId, Integer year) {
        Double sum = (double) VALUE_NOT_SET;
        Set<Quarter> allQuartersByYear = quarterService.getAllQuartersByYear(employeeId, year);
        for (Quarter quarter : allQuartersByYear) {
            List<TaxRecord> taxRecords = quarter.getTaxRecords();
            for (TaxRecord taxRecord : taxRecords) {
                sum += calculateTaxation(taxRecord);
            }
        }
        return sum;
    }

    @Override
    public Double getTotalTaxesVolumeByYear(String employeeId, Integer year) {
        Double sum = (double) VALUE_NOT_SET;
        Set<Quarter> allQuartersByYear = quarterService.getAllQuartersByYear(employeeId, year);
        for (Quarter quarter : allQuartersByYear) {
            List<TaxRecord> taxRecords = quarter.getTaxRecords();
            for (TaxRecord taxRecord : taxRecords) {
                sum += calculateTaxValue(taxRecord);
            }
        }
        return sum;
    }
}
