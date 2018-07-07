package com.provectus.taxmanagement.controller;

import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.service.ImportService;
import com.provectus.taxmanagement.service.StorageService;
import org.apache.commons.io.FileUtils;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
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

    @RequestMapping(value = "/convertTaxReport/{employeeId}", method = RequestMethod.POST)
    @ResponseBody
    public Set<Quarter> convertTaxReport(@ModelAttribute Quarter.QuarterDefinitionDTO quarterDefinitionDTO, @PathVariable String employeeId) throws IOException {
        File savedFile = storageService.storeFile(quarterDefinitionDTO.getFile());

        try {
            Set<Quarter> quarters = importService.importTaxRecordFile(savedFile, employeeId, new Quarter.QuarterDefinition(quarterDefinitionDTO.getQuarterName().toString(), quarterDefinitionDTO.getYear()));
            FileUtils.forceDelete(savedFile);
            return quarters;
        } catch (TikaException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }
}
