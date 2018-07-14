package com.provectus.taxmanagement.service;

import com.provectus.taxmanagement.entity.Quarter;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by alexey on 17.04.17.
 */
public interface ImportService {
    Quarter parseTaxRecordFile(File file, String employeeId, Quarter.QuarterDefinition quarterDefinition) throws IOException, TikaException, SAXException, ParserConfigurationException;
}
