package com.ddoddii.resume.repository;

import com.ddoddii.resume.model.question.BehaviorQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BehaviorQuestionRepository extends JpaRepository<BehaviorQuestion, Long> {
}
