package com.provectus.taxmanagement.service;

import com.provectus.taxmanagement.entity.Quarter;

/**
 * Created by alexey on 05.04.17.
 */
public interface QuarterService {
    Quarter addQuarter(String employeeId, Quarter quarter);

    String delete(String employeeId, String quarterId);
}
