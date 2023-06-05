package com.dsquare.service;

import com.dsquare.model.CountryAvgAge;
import com.dsquare.model.Employee;
import com.dsquare.repository.CountryAvgAgeRepo;
import com.dsquare.repository.EmployeeRepo;
import com.dsquare.util.FilesOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private FilesOperation filesOperation;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private CountryAvgAgeRepo countryAvgAgeRepo;

    @Override
    public void avgAgeByCountry() throws IOException {
        List<Employee> employees = filesOperation.readCSVFile("DSquare Assignment.csv");
        Map<String, Double> map = employees.stream().collect(Collectors.groupingBy(Employee::getCountry,
                Collectors.averagingInt(Employee::getAge)));
        filesOperation.createCSVFile(map);
    }

    @Override
    public void filterByName() throws IOException {
        List<Employee> employees = filesOperation.readCSVFile("DSquare Assignment.csv");
        List<Employee> fNAme = employees.stream().filter(emp -> emp.getFirstname().startsWith("V")).collect(Collectors.toList());
        List<Employee> lName = employees.stream().filter(emp -> emp.getLastName().startsWith("V")).collect(Collectors.toList());

        List<Employee> filterByName = new ArrayList<>();
        filterByName.addAll(fNAme);
        filterByName.addAll(lName);

        filesOperation.createFile(filterByName, "FilterByName.csv");
    }


    @Override
    public void sortByAge() throws IOException {
        List<Employee> employees = filesOperation.readCSVFile("DSquare Assignment.csv");
        List<Employee> sortedList = employees.stream().sorted(Comparator.comparingInt(Employee::getAge)).collect(Collectors.toList());
        filesOperation.createFile(sortedList, "SortByAge.csv");
    }

    @Async
    @Transactional
    @Override
    public CompletableFuture<Void> saveAllData(String fileName) throws IOException {
        List<Employee> employees = filesOperation.readCSVFile(fileName);
        log.info("Size Of Data Of File " + fileName + " :-" + employees.size());
        employeeRepo.saveAll(employees);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @Transactional
    @Override
    public CompletableFuture<Void> saveAll(String fileName) throws IOException {
        List<CountryAvgAge> countryAvgAges = filesOperation.readFile(fileName);
        log.info("Size Of Data Of File " + fileName + " :- " + countryAvgAges.size());
        countryAvgAgeRepo.saveAll(countryAvgAges);
        return CompletableFuture.completedFuture(null);
    }
}