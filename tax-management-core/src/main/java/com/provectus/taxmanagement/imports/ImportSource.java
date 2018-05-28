package com.provectus.taxmanagement.imports;

import com.provectus.taxmanagement.entity.Quarter;

import java.io.File;
import java.util.Set;

/**
 * Created by alexey on 17.04.17.
 */
public interface ImportSource {
    Set<Quarter> getQuarters(File file);
}
