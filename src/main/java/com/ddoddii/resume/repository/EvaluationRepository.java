package com.ddoddii.resume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
