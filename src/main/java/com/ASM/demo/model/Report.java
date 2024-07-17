package com.ASM.demo.model;

import jakarta.persistence.*;
@Entity
@Table
public class Report {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Student student;
    private Course course;
    private int totalLessons;
    private int attendedLessons;
    private double attendancePercentage;
    public Report(){};

    public Report(Long id, Student student, Course course, int totalLessons, int attendedLessons,
            double attendancePercentage) {
        super();
        this.id = id;
        this.student = student;
        this.course = course;
        this.totalLessons = totalLessons;
        this.attendedLessons = attendedLessons;
        this.attendancePercentage = attendancePercentage;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public int getTotalLessons() {
        return totalLessons;
    }

    public int getAttendedLessons() {
        return attendedLessons;
    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setTotalLessons(int totalLessons) {
        this.totalLessons = totalLessons;
    }

    public void setAttendedLessons(int attendedLessons) {
        this.attendedLessons = attendedLessons;
    }

    public void setAttendancePercentage(double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    // toString method for displaying report information
    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                ", totalLessons=" + totalLessons +
                ", attendedLessons=" + attendedLessons +
                ", attendancePercentage=" + attendancePercentage +
                '}';
    }
}
