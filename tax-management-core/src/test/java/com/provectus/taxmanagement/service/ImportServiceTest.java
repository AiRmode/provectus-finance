package com.provectus.taxmanagement.service;

import com.provectus.taxmanagement.integration.TestParent;
import org.apache.tika.exception.TikaException;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by alexey on 17.04.17.
 */
public class ImportServiceTest extends TestParent {

    @Test
    public void testImport() throws IOException, TikaException, SAXException, ParserConfigurationException {
//        importService.parseTaxRecordFile(new File(getClass().getClassLoader().getResource("UkrSib_USD_2603.pdf").getFile()), "asd");
//        importService.parseTaxRecordFile(new File(getClass().getClassLoader().getResource("testUAH2600.xls").getFile()), "asd");
    }
}
