package com.praneeth.tutorportal.service;

import com.praneeth.tutorportal.dto.*;
import com.praneeth.tutorportal.exception.NotFoundException;
import com.praneeth.tutorportal.model.*;
import com.praneeth.tutorportal.repository.AssignmentRepository;
import com.praneeth.tutorportal.repository.SubmissionRepository;
import com.praneeth.tutorportal.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;

    public AssignmentService(AssignmentRepository assignmentRepository,
                             SubmissionRepository submissionRepository,
                             UserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
    }

    public AssignmentResponse create(CreateAssignmentRequest request) {
        User student = userRepository.findById(request.studentId())
                .orElseThrow(() -> new NotFoundException("Student not found"));
        if (student.getRole() != Role.STUDENT) {
            throw new IllegalArgumentException("Assignments can only be given to students");
        }
        Assignment assignment = Assignment.builder()
                .title(request.title())
                .description(request.description())
                .dueDate(request.dueDate())
                .status(AssignmentStatus.ASSIGNED)
                .student(student)
                .build();
        return toResponse(assignmentRepository.save(assignment));
    }

    public List<AssignmentResponse> findAll() {
        return assignmentRepository.findAll().stream().map(this::toResponse).toList();
    }

    public List<AssignmentResponse> findByStudent(Long studentId) {
        return assignmentRepository.findByStudentIdOrderByDueDateAsc(studentId)
                .stream().map(this::toResponse).toList();
    }

    public AssignmentResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public AssignmentResponse update(Long id, UpdateAssignmentRequest request) {
        Assignment assignment = getOrThrow(id);
        assignment.setTitle(request.title());
        assignment.setDescription(request.description());
        assignment.setDueDate(request.dueDate());
        return toResponse(assignmentRepository.save(assignment));
    }

    public void delete(Long id) {
        Assignment assignment = getOrThrow(id);
        submissionRepository.findByAssignmentId(id).ifPresent(submissionRepository::delete);
        assignmentRepository.delete(assignment);
    }

    @Transactional
    public SubmissionResponse submit(Long assignmentId, SubmitRequest request) {
        Assignment assignment = getOrThrow(assignmentId);
        if (submissionRepository.findByAssignmentId(assignmentId).isPresent()) {
            throw new IllegalArgumentException("This assignment has already been submitted");
        }
        Submission submission = Submission.builder()
                .assignment(assignment)
                .content(request.content())
                .submittedAt(LocalDateTime.now())
                .build();
        assignment.setStatus(AssignmentStatus.SUBMITTED);
        assignmentRepository.save(assignment);
        return toResponse(submissionRepository.save(submission));
    }

    public SubmissionResponse getSubmission(Long assignmentId) {
        return submissionRepository.findByAssignmentId(assignmentId)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("No submission for this assignment"));
    }

    @Transactional
    public SubmissionResponse grade(Long submissionId, GradeRequest request) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new NotFoundException("Submission not found"));
        submission.setGrade(request.grade());
        submission.setFeedback(request.feedback());
        Assignment assignment = submission.getAssignment();
        assignment.setStatus(AssignmentStatus.GRADED);
        assignmentRepository.save(assignment);
        return toResponse(submissionRepository.save(submission));
    }

    private Assignment getOrThrow(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Assignment not found"));
    }

    private AssignmentResponse toResponse(Assignment a) {
        return new AssignmentResponse(
                a.getId(), a.getTitle(), a.getDescription(), a.getDueDate(),
                a.getStatus(), a.getStudent().getId(), a.getStudent().getName());
    }

    private SubmissionResponse toResponse(Submission s) {
        return new SubmissionResponse(
                s.getId(), s.getAssignment().getId(), s.getContent(),
                s.getGrade(), s.getFeedback(), s.getSubmittedAt());
    }
}