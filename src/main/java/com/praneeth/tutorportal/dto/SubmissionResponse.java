package com.praneeth.tutorportal.dto;

import java.time.LocalDateTime;

public record SubmissionResponse(
        Long id,
        Long assignmentId,
        String content,
        Integer grade,
        String feedback,
        LocalDateTime submittedAt
) {}