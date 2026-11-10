package com.praneeth.tutorportal.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record GradeRequest(
        @NotNull @Min(0) @Max(100) Integer grade,
        String feedback
) {}