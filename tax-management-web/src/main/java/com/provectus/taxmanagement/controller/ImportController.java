package com.provectus.taxmanagement.controller;

import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.service.ImportService;
import com.provectus.taxmanagement.service.ReportService;
import com.provectus.taxmanagement.service.StorageService;
import com.provectus.taxmanagement.service.TaxationAnalyzerService;
import org.apache.commons.io.FileUtils;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private TaxationAnalyzerService taxationAnalyzerService;

    @RequestMapping(value = "/parseTaxReport/{employeeId}", method = RequestMethod.POST, produces = "application/json")
    public Quarter parseTaxReport(@ModelAttribute Quarter.QuarterDefinitionWithAttachmentDTO quarterDefinitionWithAttachmentDTO, @PathVariable String employeeId) throws IOException {
        List<File> savedFiles = storageService.storeFiles(quarterDefinitionWithAttachmentDTO.getFiles());
        Quarter quarter = new Quarter(new Quarter.QuarterDefinition(quarterDefinitionWithAttachmentDTO.getQuarterName().name(), quarterDefinitionWithAttachmentDTO.getYear()));

        savedFiles.forEach(file -> {
            try {
                Quarter q = importService.parseTaxRecordFile(file, employeeId, new Quarter.QuarterDefinition(quarterDefinitionWithAttachmentDTO.getQuarterName().toString(), quarterDefinitionWithAttachmentDTO.getYear()));
                quarter.addTaxRecords(q.getTaxRecords());
            } catch (IOException | TikaException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        });
        savedFiles.forEach(File::delete);

        return taxationAnalyzerService.classifyTaxStatuses(quarter);
    }

    @RequestMapping(value = "/generateTaxReport", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> generateTaxReport(@RequestBody Quarter quarter) {
        Quarter analyzed = taxationAnalyzerService.trainModelBasedOnUserData(quarter);

        File taxReport = reportService.generateTaxReport(analyzed);
        String encodedPath = new String(Base64.getUrlEncoder().encode(taxReport.getPath().getBytes()));
        HashMap<String, String> map = new HashMap<>();
        map.put("location", encodedPath);
        return map;
    }

    @RequestMapping(value = "/getFile/{fileRelativePath}", method = RequestMethod.GET, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @ResponseBody
    public FileSystemResource downloadAttachment(@PathVariable String fileRelativePath, HttpServletResponse response) throws IOException {
        String path = new String(Base64.getUrlDecoder().decode(fileRelativePath.getBytes()));
        File taxReport = new File(path);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "inline; filename=" + new String(taxReport.getName().getBytes(), StandardCharsets.UTF_8.name()));
        response.setHeader("Content-Length", String.valueOf(taxReport.length()));
        FileUtils.forceDeleteOnExit(taxReport);
        return new FileSystemResource(taxReport);
    }
}
