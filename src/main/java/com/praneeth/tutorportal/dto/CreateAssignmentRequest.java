package com.praneeth.tutorportal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateAssignmentRequest(
        @NotBlank String title,
        String description,
        LocalDate dueDate,
        @NotNull Long studentId
) {}