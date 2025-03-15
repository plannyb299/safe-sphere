package com.pada.safe_sphere.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "loan-application")
@Data
public class ReportAbuse {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(nullable = false)
    private String nationalId; // National ID of the applicant

    @Column(nullable = false)
    private String applicantName;

    @Column(nullable = false)
    private String applicantEmail;

    @Column(nullable = false)
    private String faculty;

    @Column(nullable = false)
    private String Description;

    @Column(nullable = false)
    private Integer age;


}
