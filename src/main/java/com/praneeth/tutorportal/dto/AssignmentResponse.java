package com.praneeth.tutorportal.dto;

import com.praneeth.tutorportal.model.AssignmentStatus;
import java.time.LocalDate;

public record AssignmentResponse(
        Long id,
        String title,
        String description,
        LocalDate dueDate,
        AssignmentStatus status,
        Long studentId,
        String studentName
) {}