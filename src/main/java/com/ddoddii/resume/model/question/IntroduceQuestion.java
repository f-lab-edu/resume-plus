package com.ddoddii.resume.model.question;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "introduce_question")
@AllArgsConstructor
@Builder
@Getter
public class IntroduceQuestion extends BaseQuestionEntity {
}
