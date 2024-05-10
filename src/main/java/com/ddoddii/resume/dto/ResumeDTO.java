package com.ddoddii.resume.dto;

import com.ddoddii.resume.model.eunm.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class ResumeDTO {
    private Position position;
    private String content;
}
