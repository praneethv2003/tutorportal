package com.praneeth.tutorportal.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer grade;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    private LocalDateTime submittedAt;

    @OneToOne(optional = false)
    @JoinColumn(name = "assignment_id", unique = true)
    private Assignment assignment;
}