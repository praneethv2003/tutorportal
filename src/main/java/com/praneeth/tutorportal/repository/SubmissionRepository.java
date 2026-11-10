package com.praneeth.tutorportal.repository;

import com.praneeth.tutorportal.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findByAssignmentId(Long assignmentId);
}