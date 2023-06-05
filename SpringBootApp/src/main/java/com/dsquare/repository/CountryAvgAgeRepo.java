package com.dsquare.repository;

import com.dsquare.model.CountryAvgAge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryAvgAgeRepo extends JpaRepository<CountryAvgAge, String> {
}