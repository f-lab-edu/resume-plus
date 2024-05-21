package com.ddoddii.resume.controller;

import com.ddoddii.resume.dto.PersonalQuestionDTO;
import com.ddoddii.resume.service.QuestionGenService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question/")
@RequiredArgsConstructor
public class ChatController {
    private final QuestionGenService questionGenService;

    @PostMapping("/generate/{resumeId}")
    public List<PersonalQuestionDTO> generate(@PathVariable long resumeId) {
        List<PersonalQuestionDTO> questionDTOS = questionGenService.generatePersonalQuestion(resumeId);
        return questionDTOS;
    }

}
