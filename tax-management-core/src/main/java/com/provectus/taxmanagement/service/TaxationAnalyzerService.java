package com.provectus.taxmanagement.service;

import com.provectus.taxmanagement.entity.Quarter;

public interface TaxationAnalyzerService {
    Quarter analyzeTaxationBasedOnStoredData(Quarter quarter);

    Quarter analyzeTaxationFeedbackBasedOnManuallyFilteredData(Quarter quarter);
}