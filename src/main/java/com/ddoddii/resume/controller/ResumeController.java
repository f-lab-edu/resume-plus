package com.ddoddii.resume.controller;

import com.ddoddii.resume.dto.ResumeDTO;
import com.ddoddii.resume.dto.ResumeResponseDTO;
import com.ddoddii.resume.service.ResumeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resume/")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @GetMapping("/")
    public ResponseEntity<List<ResumeDTO>> getUserResumes() {
        List<ResumeDTO> resumeDTOS = resumeService.getUserResumes();
        return ResponseEntity.status(HttpStatus.OK).body(resumeDTOS);
    }


    @PostMapping("/")
    public ResponseEntity<ResumeResponseDTO> uploadResume(@RequestBody ResumeDTO resumeUploadRequestDTO) {
        ResumeResponseDTO resumeResponseDTO = resumeService.uploadResume(resumeUploadRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(resumeResponseDTO);
    }

    @PatchMapping("/{resumeId}")
    public ResponseEntity<String> modifyResume(@PathVariable long resumeId,
                                               @RequestBody ResumeDTO resumeModifyRequestDTO) {
        resumeService.modifyResume(resumeId, resumeModifyRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Resume Updated");
    }

    @DeleteMapping("/{resumeId}")
    public ResponseEntity<String> deleteResume(@PathVariable long resumeId) {
        resumeService.deleteResume(resumeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Resume Deleted");
    }


}
