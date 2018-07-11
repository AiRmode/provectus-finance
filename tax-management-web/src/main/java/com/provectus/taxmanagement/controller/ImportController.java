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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
    public void convertTaxReport(@ModelAttribute Quarter.QuarterDefinitionDTO quarterDefinitionWithAttachmentDTO, @PathVariable String employeeId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        File savedFile = storageService.storeFile(quarterDefinitionWithAttachmentDTO.getFile());

        try {
            Set<Quarter> quarters = importService.parseTaxRecordFile(savedFile, employeeId, new Quarter.QuarterDefinition(quarterDefinitionWithAttachmentDTO.getQuarterName().toString(), quarterDefinitionWithAttachmentDTO.getYear()));
            File taxReport = reportService.generateTaxReport(quarters);
            FileUtils.forceDelete(savedFile);
            String encodedPath = new String(Base64.getUrlEncoder().encode(taxReport.getPath().getBytes()));
            response.sendRedirect("/import/getFile/" + encodedPath);
        } catch (TikaException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/getFile/{fileRelativePath}", method = RequestMethod.GET, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @ResponseBody
    public FileSystemResource downloadAttachment(@PathVariable String fileRelativePath, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {
        String path = new String(Base64.getUrlDecoder().decode(fileRelativePath.getBytes()));
        File taxReport = new File(path);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "inline; filename=" + new String(taxReport.getName().getBytes(), StandardCharsets.UTF_8.name()));
        response.setHeader("Content-Length", String.valueOf(taxReport.length()));
        FileUtils.forceDeleteOnExit(taxReport);
        return new FileSystemResource(taxReport);
    }
}
