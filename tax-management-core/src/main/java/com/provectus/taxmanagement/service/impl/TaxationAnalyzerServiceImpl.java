package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.repository.TaxationDescriptionsRepository;
import com.provectus.taxmanagement.service.TaxationAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxationAnalyzerServiceImpl implements TaxationAnalyzerService {

    @Autowired
    private TaxationDescriptionsRepository taxationDescriptionsRepository;

    @Override
    public Quarter analyzeTaxationBasedOnStoredData(Quarter quarter) {
        for (TaxRecord taxRecord : quarter.getTaxRecords()) {
            taxRecord = analyze(taxRecord);
        }
        return null;
    }

    @Override
    public Quarter analyzeTaxationFeedbackBasedOnManuallyFilteredData(Quarter quarter) {
        return null;
    }

    public TaxRecord analyze(TaxRecord taxRecord) {
        return null;
    }
}
