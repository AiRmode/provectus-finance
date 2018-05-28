package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.service.ImportService;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

/**
 * Created by alexey on 17.04.17.
 */
@Service("importService")
public class ImportServiceImpl implements ImportService {

    @Override
    public Set<Quarter> importTaxRecordFile(File file, String employeeId) throws IOException, TikaException, SAXException, ParserConfigurationException {
        HSSFWorkbook workbook;// = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet;//= workbook.getSheetAt(0);
        XSSFWorkbook workbook1= new XSSFWorkbook(new FileInputStream(""));//?

        String fileType = detectFileType(file);

        Parser parser = createParser(file, fileType);

        String s = getFileContent(parser, file);
        System.out.println("File content : " + s);
        return null;
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
