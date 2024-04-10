package com.ddoddii.resume.model.question;

import com.ddoddii.resume.model.Interview;
import com.ddoddii.resume.model.Resume;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personal_question")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PersonalQuestion extends BaseQuestionEntity {

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;
}
