package com.praneeth.tutorportal.controller;

import com.praneeth.tutorportal.dto.GradeRequest;
import com.praneeth.tutorportal.dto.SubmissionResponse;
import com.praneeth.tutorportal.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final AssignmentService assignmentService;

    public SubmissionController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PutMapping("/{id}/grade")
    public SubmissionResponse grade(@PathVariable Long id,
                                    @Valid @RequestBody GradeRequest request) {
        return assignmentService.grade(id, request);
    }
}