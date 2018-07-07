package com.provectus.taxmanagement.service;

import com.provectus.taxmanagement.entity.TaxRecord;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface TaxReportService {
    List<TaxRecord> parseDocument(File document) throws IOException, ParseException;
}
