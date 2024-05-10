package com.ddoddii.resume.service;

import com.ddoddii.resume.dto.ResumeDTO;
import com.ddoddii.resume.dto.ResumeResponseDTO;
import com.ddoddii.resume.error.errorcode.ResumeErrorCode;
import com.ddoddii.resume.error.exception.NotExistResumeException;
import com.ddoddii.resume.model.Resume;
import com.ddoddii.resume.model.User;
import com.ddoddii.resume.repository.ResumeRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserService userService;

    // 사용자 레쥬메 업로드
    public ResumeResponseDTO uploadResume(ResumeDTO resumeUploadRequestDTO) {
        User user = userService.getCurrentUser();
        Resume resume = Resume.builder()
                .position(resumeUploadRequestDTO.getPosition())
                .content(resumeUploadRequestDTO.getContent())
                .user(user)
                .build();

        resumeRepository.save(resume);

        return ResumeResponseDTO.builder()
                .userId(user.getId())
                .resumeId(resume.getId())
                .build();
    }

    // 사용자 레쥬메 업데이트
    public void modifyResume(long resumeId, ResumeDTO resumeModifyRequestDTO) {
        Resume resume = resumeRepository.findById(resumeId).orElseThrow(() -> new NotExistResumeException(
                ResumeErrorCode.NOT_EXIST_RESUME));
        resume.setContent(resumeModifyRequestDTO.getContent());
        resume.setPosition(resumeModifyRequestDTO.getPosition());
        resumeRepository.save(resume);
    }

    // 사용자 레쥬메 보기
    public List<ResumeDTO> getUserResumes() {
        User user = userService.getCurrentUser();
        List<Resume> resumes = resumeRepository.findByUser(user);
        return resumes.stream()
                .map(resume -> ResumeDTO.builder()
                        .position(resume.getPosition())
                        .content(resume.getContent())
                        .build())
                .collect(Collectors.toList());
    }

}
