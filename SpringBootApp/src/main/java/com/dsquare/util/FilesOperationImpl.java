package com.dsquare.util;

import com.dsquare.model.CountryAvgAge;
import com.dsquare.model.Employee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class FilesOperationImpl implements FilesOperation {
    @Override
    public List<Employee> readCSVFile(String fileName) throws IOException {
        List<Employee> employeeList = new ArrayList<>();

        Reader reader = new FileReader(new ClassPathResource("static/" + fileName).getFile().getAbsolutePath());
        CSVParser csvParser = CSVFormat.DEFAULT.withHeader("id", "firstname", "lastname", "age", "Country")
                .withIgnoreHeaderCase().parse(reader);

        for (CSVRecord record : csvParser) {
            if (!record.get("id").equals("id")) {
                employeeList.add(new Employee(Integer.parseInt(record.get("id")), record.get("firstname"),
                        record.get("lastname"), Integer.parseInt(record.get("age")), record.get("Country")));
            }
        }
        csvParser.close();
        reader.close();
        return employeeList;
    }

    @Override
    public void createFile(List<Employee> employees, String fileName) throws IOException {
        FileWriter writer = new FileWriter(String.valueOf(
                Paths.get("./src/main/resources/static/" + fileName).toAbsolutePath().normalize()));
        CSVPrinter csvPrinter = new CSVPrinter(writer,
                CSVFormat.DEFAULT.withHeader("id", "firstname", "lastname", "age", "Country"));

        for (Employee employee : employees) {
            csvPrinter.printRecord(employee.getId(), employee.getFirstname(), employee.getLastName(), employee.getAge(),
                    employee.getCountry());
        }
        csvPrinter.flush();
        csvPrinter.close();
        writer.close();
    }

    @Override
    public void createCSVFile(Map<String, Double> map) throws IOException {
        FileWriter writer = new FileWriter(String.valueOf(
                Paths.get("./src/main/resources/static/AvgAgeByCountry.csv").toAbsolutePath().normalize()));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Country", "AvgAge"));

        for (Map.Entry<String, Double> entry : map.entrySet()) {
            csvPrinter.printRecord(entry.getKey(), String.format("%.2f", entry.getValue()));
        }
        csvPrinter.flush();
        csvPrinter.close();
        writer.close();
    }

    @Override
    public List<CountryAvgAge> readFile(String fileName) throws IOException {
        List<CountryAvgAge> countryAvgAges = new ArrayList<>();

        Reader reader = new FileReader(new ClassPathResource("static/" + fileName).getFile().getAbsolutePath());
        CSVParser csvParser = CSVFormat.DEFAULT.withHeader("Country", "AvgAge").withIgnoreHeaderCase().parse(reader);
        for (CSVRecord record : csvParser) {
            if (!record.get("Country").equals("Country")) {
                countryAvgAges.add(new CountryAvgAge(record.get("Country"), Double.parseDouble(record.get("AvgAge"))));
            }
        }
        csvParser.close();
        reader.close();
        return countryAvgAges;
    }
}