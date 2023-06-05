package com.dsquare.controller;

import com.dsquare.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/avg_age_by_country")
    public ResponseEntity<String> avgAgeByCountry() throws IOException {
        employeeService.avgAgeByCountry();
        return ResponseEntity.ok("New File Created Successfully");
    }

    @GetMapping("/filter_by_name")
    public ResponseEntity<String> filterByName() throws IOException {
        employeeService.filterByName();
        return ResponseEntity.ok("New File Created Successfully");
    }

    @GetMapping("/sort_by_age")
    public ResponseEntity<String> sortByAge() throws IOException {
        employeeService.sortByAge();
        return ResponseEntity.ok("New File Created Successfully");
    }

    @PostMapping("/save-all")
    public ResponseEntity<String> saveAllData() throws IOException {
        List<String> strings = List.of("FilterByName.csv", "SortByAge.csv", "AvgAgeByCountry.csv");
        CompletableFuture<Void> futures[] = new CompletableFuture[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            if (i <= 1) {
                futures[i] = employeeService.saveAllData(strings.get(i));
            } else {
                futures[i] = employeeService.saveAll(strings.get(i));
            }
        }
        CompletableFuture.allOf(futures).join();
        return ResponseEntity.ok("All Data Saved Successfully");
    }
}