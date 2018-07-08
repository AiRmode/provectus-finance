package com.provectus.taxmanagement.controller;

import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.service.ImportService;
import com.provectus.taxmanagement.service.ReportService;
import com.provectus.taxmanagement.service.StorageService;
import org.apache.commons.io.FileUtils;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Created by alexey on 22.04.17.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/import")
public class ImportController {

    @Autowired
    @Qualifier("importService")
    private ImportService importService;

    @Autowired
    @Qualifier("storageService")
    private StorageService storageService;

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/convertTaxReport/{employeeId}", method = RequestMethod.POST, produces = "application/*")
    @ResponseBody
    public FileSystemResource convertTaxReport(@ModelAttribute Quarter.QuarterDefinitionDTO quarterDefinitionDTO, @PathVariable String employeeId) throws IOException {
        File savedFile = storageService.storeFile(quarterDefinitionDTO.getFile());

        try {
            Set<Quarter> quarters = importService.importTaxRecordFile(savedFile, employeeId, new Quarter.QuarterDefinition(quarterDefinitionDTO.getQuarterName().toString(), quarterDefinitionDTO.getYear()));
            File taxReport = reportService.createTaxReport(quarters);
            FileUtils.forceDelete(savedFile);
            return new FileSystemResource(taxReport);
        } catch (TikaException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
