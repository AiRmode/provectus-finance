package com.provectus.taxmanagement.service;

import com.provectus.taxmanagement.entity.TaxRecord;

import java.io.File;
import java.util.List;

public interface TaxReportService {
    List<TaxRecord> parseDocument(File document);

    List<TaxRecord> parseXlsDocument(File document);
}
