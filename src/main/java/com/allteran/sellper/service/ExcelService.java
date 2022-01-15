package com.allteran.sellper.service;

import com.allteran.sellper.domain.RepairOrder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ExcelService {
    @Value("${excel.header.template.acceptance}")
    private String ACCEPTANCE_HEADER;
    @Value("${excel.header.template.repair}")
    private String REPAIR_HEADER;

    private static final String ACCEPTANCE_FILENAME_PREFIX = "ACC_CERTIFICATE_";
    private static final String XLSX_TEMPLATE_DIR = "src" + File.separator + "main" + File.separator + "resources" + File.separator +"static"+File.separator +"files" + File.separator;
    private static final String ACCEPTANCE_XLSX_TEMPLATE_NAME = "ACCEPTANCE_TEMPLATE.xlsx";
    private static final String REPAIR_XLSX_TEMPLATE_NAME = "REPAIR_TEMPLATE.xlsx";
    private static final String REPAIR_FILENAME_PREFIX = "REPAIR_CERTIFICATE_";


    public ByteArrayInputStream generateAcceptanceCertificate(@NotNull RepairOrder order) throws IOException {
        File currentDir = new File(".");
        String path = currentDir.getAbsolutePath().substring(0, currentDir.getAbsolutePath().length() - 1) +
                XLSX_TEMPLATE_DIR + ACCEPTANCE_XLSX_TEMPLATE_NAME;
        FileInputStream file = null;
        Workbook workbook = null;
        try {
            file = new FileInputStream(path);
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert workbook != null;
        Sheet mainSheet = workbook.getSheetAt(0);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if(order.getCreationDate() == null) {
            order.setCreationDate(LocalDateTime.now());
        }
        mainSheet.getRow(6).getCell(1).setCellValue(ACCEPTANCE_HEADER + " " + dateFormat.format(order.getCreationDate()));
        mainSheet.getRow(36).getCell(1).setCellValue(ACCEPTANCE_HEADER + " " + dateFormat.format(order.getCreationDate()));

        //set POS params to cell
        mainSheet.getRow(2).getCell(1).setCellValue(order.getPos().getCity() + ", " + order.getPos().getStreet() + " " + order.getPos().getBuilding());
        mainSheet.getRow(32).getCell(1).setCellValue(order.getPos().getCity() + ", " + order.getPos().getStreet() + " " + order.getPos().getBuilding());

        //set author to cell
        mainSheet.getRow(4).getCell(1).setCellValue(order.getAuthor().getFirstName() + " " + order.getAuthor().getLastName());
        mainSheet.getRow(34).getCell(1).setCellValue(order.getAuthor().getFirstName() + " " + order.getAuthor().getLastName());

        //set customer's name to cell
        mainSheet.getRow(7).getCell(2).setCellValue(order.getCustomerName());
        mainSheet.getRow(37).getCell(2).setCellValue(order.getCustomerName());

        //set customer's phone
        mainSheet.getRow(8).getCell(2).setCellValue(order.getCustomerPhone());
        mainSheet.getRow(38).getCell(2).setCellValue(order.getCustomerPhone());

        //set device name and type
        mainSheet.getRow(9).getCell(2).setCellValue(order.getDeviceType().getName() + " " + order.getDeviceName());
        mainSheet.getRow(39).getCell(2).setCellValue(order.getDeviceType().getName() + " " + order.getDeviceName());

        //set SN
        mainSheet.getRow(10).getCell(2).setCellValue(order.getSerialNumber());
        mainSheet.getRow(40).getCell(2).setCellValue(order.getSerialNumber());

        //set defect
        mainSheet.getRow(11).getCell(2).setCellValue(order.getDefect());
        mainSheet.getRow(41).getCell(2).setCellValue(order.getDefect());

        //set equip set
        mainSheet.getRow(12).getCell(2).setCellValue(order.getEquipSet());
        mainSheet.getRow(42).getCell(2).setCellValue(order.getEquipSet());

        //set appearance
        mainSheet.getRow(13).getCell(2).setCellValue(order.getAppearance());
        mainSheet.getRow(43).getCell(2).setCellValue(order.getAppearance());

        //set preliminary price
        mainSheet.getRow(14).getCell(2).setCellValue(order.getPreliminaryPrice());
        mainSheet.getRow(44).getCell(2).setCellValue(order.getPreliminaryPrice());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    public ByteArrayInputStream generateRepairCertificate(@NotNull RepairOrder order) throws IOException {
        File currentDir = new File(".");
        String path = currentDir.getAbsolutePath().substring(0, currentDir.getAbsolutePath().length() - 1) +
                XLSX_TEMPLATE_DIR + REPAIR_XLSX_TEMPLATE_NAME;
        FileInputStream file;
        Workbook workbook = null;
        try {
            file = new FileInputStream(path);
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert workbook != null;
        Sheet mainSheet = workbook.getSheetAt(0);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        //header
        mainSheet.getRow(7).getCell(1).setCellValue(REPAIR_HEADER + " " + dateFormat.format(order.getIssueDate()));
        mainSheet.getRow(41).getCell(1).setCellValue(REPAIR_HEADER + " " + dateFormat.format(order.getIssueDate()));

        //POS params
        mainSheet.getRow(2).getCell(1).setCellValue(order.getPos().getCity() + ", " + order.getPos().getStreet() + ", " + order.getPos().getBuilding());
        mainSheet.getRow(36).getCell(1).setCellValue(order.getPos().getCity() + ", " + order.getPos().getStreet() + ", " + order.getPos().getBuilding());

        //author
        mainSheet.getRow(4).getCell(1).setCellValue(order.getAuthor().getFirstName() + " " + order.getAuthor().getLastName());
        mainSheet.getRow(38).getCell(1).setCellValue(order.getAuthor().getFirstName() + " " + order.getAuthor().getLastName());

        //customer name
        mainSheet.getRow(8).getCell(2).setCellValue(order.getCustomerName());
        mainSheet.getRow(42).getCell(2).setCellValue(order.getCustomerName());

        //device name and type
        mainSheet.getRow(9).getCell(2).setCellValue(order.getDeviceType().getName() + " " + order.getDeviceName());
        mainSheet.getRow(43).getCell(2).setCellValue(order.getDeviceType().getName() + " " + order.getDeviceName());

        //SN or IMEI
        mainSheet.getRow(10).getCell(2).setCellValue(order.getSerialNumber());
        mainSheet.getRow(44).getCell(2).setCellValue(order.getSerialNumber());

        //defect
        mainSheet.getRow(11).getCell(2).setCellValue(order.getDefect());
        mainSheet.getRow(45).getCell(2).setCellValue(order.getDefect());

        //warranty period
        mainSheet.getRow(13).getCell(2).setCellValue(order.getWarranty());
        mainSheet.getRow(47).getCell(2).setCellValue(order.getWarranty());

        //performed actions
        mainSheet.getRow(16).getCell(1).setCellValue(order.getPerformedActions());
        mainSheet.getRow(50).getCell(1).setCellValue(order.getPerformedActions());

        //total price
        mainSheet.getRow(16).getCell(9).setCellValue(order.getTotalPrice());
        mainSheet.getRow(18).getCell(9).setCellValue(order.getTotalPrice());
        mainSheet.getRow(50).getCell(9).setCellValue(order.getTotalPrice());
        mainSheet.getRow(52).getCell(9).setCellValue(order.getTotalPrice());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
