package com.ddoddii.resume.service;

import com.ddoddii.resume.dto.PersonalQuestionDTO;
import com.ddoddii.resume.error.errorcode.OpenAiErrorCode;
import com.ddoddii.resume.error.errorcode.ResumeErrorCode;
import com.ddoddii.resume.error.exception.JsonParseException;
import com.ddoddii.resume.error.exception.NotExistResumeException;
import com.ddoddii.resume.error.exception.NotResumeOwnerException;
import com.ddoddii.resume.model.Resume;
import com.ddoddii.resume.model.User;
import com.ddoddii.resume.model.eunm.Position;
import com.ddoddii.resume.model.question.PersonalQuestion;
import com.ddoddii.resume.repository.PersonalQuestionRepository;
import com.ddoddii.resume.repository.ResumeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application-openai.yaml")
@Slf4j
public class QuestionGenService {
    @Value("classpath:/prompts/perQ-gen-system.st")
    private Resource perQGenSystemResource;

    @Value("classpath:/prompts/perQ-gen-user.st")
    private Resource perQGenUserResource;


    private final OpenAiChatClient chatClient;
    private final UserService userService;
    private final PersonalQuestionRepository personalQuestionRepository;
    private final ResumeRepository resumeRepository;
    private final ObjectMapper objectMapper;


    //개인 질문 생성
    public List<PersonalQuestionDTO> generatePersonalQuestion(long resumeId) {
        Resume resume = checkResumeOwner(resumeId);
        Position position = resume.getPosition();
        String resumeContent = resume.getContent();
        Prompt prompt = generatePrompt(position.getName(), resumeContent);
        ChatResponse response = chatClient.call(prompt);
        List<PersonalQuestionDTO> questionDTOs = parseQuestions(response);

        savePersonalQuestions(questionDTOs, resume);
        return questionDTOs;
    }

    // 레쥬메 권한 확인
    private Resume checkResumeOwner(long resumeId) {
        User currentUser = userService.getCurrentUser();
        Resume resume = resumeRepository.findById(resumeId).orElseThrow(() -> new NotExistResumeException(
                ResumeErrorCode.NOT_EXIST_RESUME));
        if (resume.getUser() != currentUser) {
            throw new NotResumeOwnerException(ResumeErrorCode.NOT_RESUME_OWNER);
        }
        return resume;
    }

    // 포지션, 레쥬메 기반으로 프롬프트 생성
    private Prompt generatePrompt(String position, String resumeContent) {
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(perQGenSystemResource);
        Message systemMessage = systemPromptTemplate.createMessage();
        PromptTemplate userPromptTemplate = new PromptTemplate(perQGenUserResource);
        Message userMessage = userPromptTemplate.createMessage(
                Map.of("position", position, "resume", resumeContent));
        return new Prompt(List.of(userMessage, systemMessage));
    }

    // Sting 에서 Json 형태로 파싱
    private List<PersonalQuestionDTO> parseQuestions(ChatResponse response) {
        String jsonResponse = response.getResult().getOutput().getContent();
        List<PersonalQuestionDTO> questionList = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            for (JsonNode questionNode : rootNode) {
                String question = questionNode.get("question").asText();
                log.info("parsed question :" + question);
                List<String> criteria = new ArrayList<>();
                for (JsonNode criteriaNode : questionNode.get("criteria")) {
                    criteria.add(criteriaNode.asText());
                }
                questionList.add(PersonalQuestionDTO.builder().question(question).criteria(criteria).build());
            }
        } catch (IOException e) {
            throw new JsonParseException(OpenAiErrorCode.JSON_PARSE_ERROR);
        }
        return questionList;
    }

    // 개인 질문 데이터베이스에 저장
    private void savePersonalQuestions(List<PersonalQuestionDTO> personalQuestionDTOS, Resume resume) {
        for (PersonalQuestionDTO personalQuestionDTO : personalQuestionDTOS) {
            String criterias = StringUtils.join(personalQuestionDTO.getCriteria(), ", ");
            PersonalQuestion personalQuestion = PersonalQuestion.builder()
                    .resume(resume)
                    .question(personalQuestionDTO.getQuestion())
                    .criteria(criterias)
                    .build();
            personalQuestionRepository.save(personalQuestion);
        }
    }


}
