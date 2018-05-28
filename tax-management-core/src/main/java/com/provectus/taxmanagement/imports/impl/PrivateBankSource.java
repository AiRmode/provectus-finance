package com.provectus.taxmanagement.imports.impl;

import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.imports.ImportSource;

import java.io.File;
import java.util.Set;

/**
 * Created by alexey on 17.04.17.
 */
public class PrivateBankSource implements ImportSource {
    @Override
    public Set<Quarter> getQuarters(File file) {
        return null;
    }
}
