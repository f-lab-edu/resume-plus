package com.ddoddii.resume.repository;

import com.ddoddii.resume.model.question.TechQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechQuestionRepository extends JpaRepository<TechQuestion, Long> {
}
