package com.praneeth.tutorportal.dto;

import jakarta.validation.constraints.NotBlank;

public record SubmitRequest(@NotBlank String content) {}
