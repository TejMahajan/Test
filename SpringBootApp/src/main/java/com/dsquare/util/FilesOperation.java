package com.dsquare.util;

import com.dsquare.model.CountryAvgAge;
import com.dsquare.model.Employee;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FilesOperation {

    List<Employee> readCSVFile(String fileName) throws IOException;

    void createFile(List<Employee> employees, String fileName) throws IOException;

    void createCSVFile(Map<String, Double> map) throws IOException;

    List<CountryAvgAge> readFile(String fileName) throws IOException;
}