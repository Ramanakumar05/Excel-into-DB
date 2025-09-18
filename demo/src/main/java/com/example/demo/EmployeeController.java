package com.example.demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/excel")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        service.saveFromExcel(file);
        return "Excel data uploaded!";
    }

    @GetMapping("/employees")
    public List<Employee> getAll() {
        return service.getAll();
    }
}
