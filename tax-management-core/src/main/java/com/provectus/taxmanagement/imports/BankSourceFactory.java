package com.provectus.taxmanagement.imports;

import com.provectus.taxmanagement.imports.impl.PrivateBankSource;
import com.provectus.taxmanagement.imports.impl.UkrSibSource;

/**
 * Created by alexey on 22.04.17.
 */
public abstract class BankSourceFactory {

    public ImportSource getImportSource(String content) {
        if (content.contains("АТ \"УкрСиббанк\"") || content.contains("код банку 351005")) {
            return new UkrSibSource();
        } else {
            return new PrivateBankSource();
        }
    }
}
