package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.service.ImportService;
import com.provectus.taxmanagement.service.TaxReportService;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by alexey on 17.04.17.
 */
@Service("importService")
public class ImportServiceImpl implements ImportService {

    @Autowired
    @Qualifier("taxReportService")
    private TaxReportService taxReportService;

    private static final Logger logger = LoggerFactory.getLogger(ImportServiceImpl.class);

    @Override
    public Quarter parseTaxRecordFile(File file, String employeeId, Quarter.QuarterDefinition quarterDefinition) throws IOException, TikaException, SAXException, ParserConfigurationException {
        List<TaxRecord> taxRecords = null;
        taxRecords = taxReportService.parseDocument(file);
        if (taxRecords == null || taxRecords.isEmpty()) {
            taxRecords = taxReportService.parseXlsDocument(file);
        }
        Quarter q = new Quarter(quarterDefinition);
        q.addTaxRecords(taxRecords);

        return q;


//        String fileType = detectFileType(file);

//        Parser parser = createParser(file, fileType);

//        String s = getFileContent(parser, file);
//        logger.info("File content : " + s);
//        return null;
    }

    private String detectFileType(File file) throws IOException {
        Tika tika = new Tika();
        return tika.detect(file);
    }

    private Parser createParser(File file, String fileType) {
        String fileExtension = FilenameUtils.getExtension(file.getName());
        if (fileExtension.equalsIgnoreCase("pdf")) {
            return new PDFParser();
        } else {
            return new AutoDetectParser();
        }
    }

    private String getFileContent(Parser parser, File file) throws IOException, TikaException, SAXException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(file);
        ParseContext context = new ParseContext();
        //parsing the file
        parser.parse(inputstream, handler, metadata, context);
        inputstream.close();
        return handler.toString();
    }
}
