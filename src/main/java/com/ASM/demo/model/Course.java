package com.ASM.demo.model;

import java.io.Serializable;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "courses")
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_code", nullable = false)
    private String courseCode;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(nullable = false)
    private String department;
    
    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private Teacher instructor;

    @Column(name = "credit_hours", nullable = false)
    private int creditHours;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons;

    // Constructors
    public Course() {
    }

    public Course(String courseCode, String courseName, String department, Teacher instructor, int creditHours) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.department = department;
        this.instructor = instructor;
        this.creditHours = creditHours;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Teacher getInstructor() {
        return instructor;
    }

    public void setInstructor(Teacher instructor) {
        this.instructor = instructor;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", department='" + department + '\'' +
                ", instructor=" + instructor +
                ", creditHours=" + creditHours +
                '}';
    }
}
