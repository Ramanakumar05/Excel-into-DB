package com.example.demo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
public class EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public void saveFromExcel(MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            List<Employee> employees = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // start from 1 (skip header)
                Row row = sheet.getRow(i);
                Employee e = new Employee();
                e.setName(row.getCell(0).getStringCellValue());
                e.setEmail(row.getCell(1).getStringCellValue());
                e.setSalary(row.getCell(2).getNumericCellValue());
                employees.add(e);
            }

            repo.saveAll(employees);
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException("Error reading Excel file: " + e.getMessage());
        }
    }

    public List<Employee> getAll() {
        return repo.findAll();
    }
}
