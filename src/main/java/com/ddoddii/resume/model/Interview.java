package com.ddoddii.resume.model;

import com.ddoddii.resume.model.eunm.InterviewFormat;
import com.ddoddii.resume.model.eunm.InterviewRound;
import com.ddoddii.resume.model.eunm.InterviewTarget;
import com.ddoddii.resume.model.eunm.InterviewType;
import com.ddoddii.resume.model.question.PersonalQuestion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
@ManyToOne : 자식 엔티티(FK를 들고있는 쪽)
@ManyToOne(optional = false) : FK 에 null 값을 허용하지 않는다.
@OneToMany : 부모 엔티티(참조 당하는 쪽)
Interview(다) - Resume(1) : 양방향
Interview(다) - User(1) : 양방향
 */

@Entity
@Table(name = "interview")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Interview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "interview_target")
    private InterviewTarget interviewTarget;

    @Enumerated(EnumType.STRING)
    @Column(name = "interview_round")
    private InterviewRound interviewRound;

    @Enumerated(EnumType.STRING)
    @Column(name = "interview_format")
    private InterviewFormat interviewFormat;

    @Enumerated(EnumType.STRING)
    @Column(name = "interview_type")
    private InterviewType interviewType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "interview")
    private List<PersonalQuestion> personalQuestions;

    @OneToOne(mappedBy = "interview")
    private Evaluation evaluation;


}
