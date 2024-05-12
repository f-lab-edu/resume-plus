package com.ddoddii.resume.model.question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tech_question")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class TechQuestion extends BaseQuestionEntity {
    @Column(name = "topic")
    private String topic;

    @Column(name = "example_answer")
    private String exampleAnswer;
}
