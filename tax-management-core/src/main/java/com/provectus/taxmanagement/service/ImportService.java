package com.provectus.taxmanagement.service;

import com.provectus.taxmanagement.entity.Quarter;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Created by alexey on 17.04.17.
 */
public interface ImportService {
    Set<Quarter> importTaxRecordFile(File file, String employeeId) throws IOException, TikaException, SAXException, ParserConfigurationException;
}
