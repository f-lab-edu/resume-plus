package com.ddoddii.resume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalQuestionRepository extends
        JpaRepository<com.ddoddii.resume.model.question.PersonalQuestion, Long> {
}
