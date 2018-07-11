package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.entity.Quarter;
import com.provectus.taxmanagement.entity.TaxRecord;
import com.provectus.taxmanagement.service.ReportService;
import com.provectus.taxmanagement.service.StorageService;
import com.provectus.taxmanagement.service.TaxCalculationService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ReportServiceImpl implements ReportService {
    public static final String DATE_PATTERN_FOR_REPORT = "dd/MM/yyyy";
    @Autowired
    private StorageService storageService;

    @Autowired
    private TaxCalculationService taxCalculationService;

    public File generateTaxReport(Set<Quarter> quarterSet) {

        Workbook wb = new XSSFWorkbook();

        Sheet summary = createStaticHeaders(wb);

        quarterSet.forEach(quarter -> {
            fillDataToRow(summary, quarter, 3, 0);
        });

        return storageService.saveWorkbook(wb, "налоги.xlsx");
    }

    private void fillDataToRow(Sheet summary, Quarter quarter, int row, int cell) {
        List<TaxRecord> taxRecords = quarter.getTaxRecords();
        for (TaxRecord taxRecord : taxRecords) {
            Row currentRow = summary.getRow(row);
            Cell taxCell = currentRow.getCell(cell++);
            taxCell.setCellValue(taxRecord.getCounterpartyName());
            taxCell = currentRow.getCell(cell++);
            taxCell.setCellValue(new SimpleDateFormat(DATE_PATTERN_FOR_REPORT).format(taxRecord.getReceivingDate()));
            taxCell = currentRow.getCell(cell++);
            taxCell.setCellValue(taxRecord.getUsdRevenue());
            taxCell = currentRow.getCell(cell++);
            taxCell.setCellValue(taxRecord.getUahRevenue());
            taxCell = currentRow.getCell(cell++);
            taxCell.setCellValue(taxRecord.getExchRateUsdUahNBUatReceivingDate());
            taxCell = currentRow.getCell(cell++);
            taxCell.setCellValue(taxRecord.getExchRateUsdUahNBUatReceivingDate() * taxRecord.getUsdRevenue());
            taxCell = currentRow.getCell(cell++);
            taxCell.setCellValue(taxCalculationService.calculateTaxation(taxRecord));
            cell = 0;
            row++;
        }
        Row sumRow = summary.getRow(row);
        Cell sumTitleCell = sumRow.getCell(0);
        sumTitleCell.setCellValue("Итого");
        Cell sumValueCell = sumRow.getCell(1);
        summary.addMergedRegion(new CellRangeAddress(
                row, //first row (0-based)
                row, //last row  (0-based)
                1, //first column (0-based)
                6  //last column  (0-based)
        ));
        sumValueCell.setCellValue(taxCalculationService.calculateTaxation(quarter));
        Cell taxValueCell = sumRow.getCell(8);
        taxValueCell.setCellValue(taxCalculationService.calculateTaxValue(quarter));
        summary.addMergedRegion(new CellRangeAddress(
                3, //first row (0-based)
                3 + taxRecords.size() - 1, //last row  (0-based)
                7, //first column (0-based)
                7  //last column  (0-based)
        ));
        Row quarterRow = summary.getRow(3);
        Cell quarterCell = quarterRow.getCell(7);
        quarterCell.setCellValue(quarter.getQuarterDefinition().getQuarterName() + " " + quarter.getQuarterDefinition().getYear());
    }

    private Sheet createStaticHeaders(Workbook wb) {
        Sheet summary = wb.createSheet("Summary");

        initRowsAndCells(summary);

        int rowNumber = 0;
        int cellNumber = 0;

        Row row = summary.createRow(rowNumber++);
        Cell cell = row.createCell(3);
        cell.setCellValue("ФИО");

        row = summary.createRow(rowNumber++);
        Cell cell2 = row.createCell(2);
        summary.addMergedRegion(new CellRangeAddress(
                1, //first row (0-based)
                1, //last row  (0-based)
                2, //first column (0-based)
                3  //last column  (0-based)
        ));
        cell2.setCellValue("Валюта Поступления");

        cell = row.createCell(8);
        summary.addMergedRegion(new CellRangeAddress(
                1, //first row (0-based)
                1, //last row  (0-based)
                8, //first column (0-based)
                10  //last column  (0-based)
        ));
        cell.setCellValue("Единый Налог");

        List<String> headers = new ArrayList<>();
        headers.add("Наименование Контрагента");
        headers.add("Дата поступения денежных средств");
        headers.add("Доллары");
        headers.add("Гривна");
        headers.add("Курс НБУ на дату поступления");
        headers.add("Эквивалент в гривне по курсу НБУ к налогообложению");
        headers.add("Сумма к налогообложению");
        headers.add("Период");
        headers.add("Начислено");
        headers.add("Оплачено ");
        headers.add("Дата оплаты");

        row = summary.createRow(rowNumber);
        for (String header : headers) {
            Cell cell1 = row.createCell(cellNumber++);
            cell1.setCellValue(header);
        }

        return summary;
    }

    private void initRowsAndCells(Sheet summary) {
        for (int i = 0; i <= 33; i++) {
            Row row = summary.createRow(i);
            for (int j = 0; j < 9; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue("");
            }
        }
    }
}
