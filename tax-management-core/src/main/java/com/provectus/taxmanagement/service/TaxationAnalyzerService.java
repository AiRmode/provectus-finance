package com.provectus.taxmanagement.service;

import com.provectus.taxmanagement.entity.Quarter;

public interface TaxationAnalyzerService {
    Quarter classifyTaxStatuses(Quarter quarter);

    Quarter trainModelBasedOnUserData(Quarter quarter);
}
