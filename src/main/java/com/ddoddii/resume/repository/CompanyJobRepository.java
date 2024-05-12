package com.ddoddii.resume.repository;

import com.ddoddii.resume.model.company.CompanyJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyJobRepository extends JpaRepository<CompanyJob, Long> {
}
