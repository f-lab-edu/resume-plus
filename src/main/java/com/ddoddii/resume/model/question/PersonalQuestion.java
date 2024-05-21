package com.ddoddii.resume.model.question;

import com.ddoddii.resume.model.Interview;
import com.ddoddii.resume.model.Resume;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "personal_question")
@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalQuestion extends BaseQuestionEntity {

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;
}
