package com.praneeth.tutorportal.dto;

import com.praneeth.tutorportal.model.Role;

public record UserResponse(Long id, String name, String email, Role role) {}