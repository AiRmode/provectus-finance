package com.provectus.taxmanagement.service;

import com.provectus.taxmanagement.entity.Quarter;

import java.io.File;
import java.util.Set;

public interface ReportService {
    File createTaxReport(Set<Quarter> quarterSet);
}
