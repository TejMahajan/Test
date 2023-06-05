package com.dsquare.service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface EmployeeService {
    void avgAgeByCountry() throws IOException;

    void filterByName() throws IOException;

    void sortByAge() throws IOException;

    CompletableFuture<Void> saveAllData(String fileName) throws IOException;

    CompletableFuture<Void> saveAll(String fileName) throws IOException;
}