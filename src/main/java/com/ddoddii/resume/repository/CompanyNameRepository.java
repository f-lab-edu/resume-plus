package com.ddoddii.resume.repository;

import com.ddoddii.resume.model.company.CompanyName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyNameRepository extends JpaRepository<CompanyName, Long> {
}
