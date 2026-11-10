package com.praneeth.tutorportal.controller;

import com.praneeth.tutorportal.dto.*;
import com.praneeth.tutorportal.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssignmentResponse create(@Valid @RequestBody CreateAssignmentRequest request) {
        return assignmentService.create(request);
    }

    @GetMapping
    public List<AssignmentResponse> findAll(@RequestParam(required = false) Long studentId) {
        return studentId == null
                ? assignmentService.findAll()
                : assignmentService.findByStudent(studentId);
    }

    @GetMapping("/{id}")
    public AssignmentResponse findById(@PathVariable Long id) {
        return assignmentService.findById(id);
    }

    @PutMapping("/{id}")
    public AssignmentResponse update(@PathVariable Long id,
                                     @Valid @RequestBody UpdateAssignmentRequest request) {
        return assignmentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        assignmentService.delete(id);
    }

    @PostMapping("/{id}/submission")
    @ResponseStatus(HttpStatus.CREATED)
    public SubmissionResponse submit(@PathVariable Long id,
                                     @Valid @RequestBody SubmitRequest request) {
        return assignmentService.submit(id, request);
    }

    @GetMapping("/{id}/submission")
    public SubmissionResponse getSubmission(@PathVariable Long id) {
        return assignmentService.getSubmission(id);
    }
}