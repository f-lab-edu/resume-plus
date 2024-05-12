package com.ddoddii.resume.repository;

import com.ddoddii.resume.model.question.IntroduceQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntroduceQuestionRepository extends JpaRepository<IntroduceQuestion, Long> {
}
