package com.ddoddii.resume.repository;

import com.ddoddii.resume.model.company.CompanyDept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDeptRepository extends JpaRepository<CompanyDept, Long> {
}
