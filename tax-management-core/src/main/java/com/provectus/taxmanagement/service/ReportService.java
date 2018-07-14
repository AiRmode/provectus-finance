package com.provectus.taxmanagement.service;

import com.provectus.taxmanagement.entity.Quarter;

import java.io.File;

public interface ReportService {
    File generateTaxReport(Quarter quarter);
}
