package com.praneeth.tutorportal.repository;

import com.praneeth.tutorportal.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByStudentIdOrderByDueDateAsc(Long studentId);
}
