package com.praneeth.tutorportal.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record UpdateAssignmentRequest(
        @NotBlank String title,
        String description,
        LocalDate dueDate
) {}