package com.ASM.demo.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(name = "date", columnDefinition = "TIMESTAMP")
    private LocalDate date;

    @Column(name = "present" ,columnDefinition="BOOLEAN")
    private boolean present;
    

    public Attendance() {

    };

    public Attendance(Student student, Lesson lesson, LocalDate date, boolean present) {
        super();
        this.student = student;
        this.lesson = lesson;
        this.date = date;
        this.present = present;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isPresent() {
        return present;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    // toString method for displaying attendance information
    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", student=" + student +
                ", lesson=" + lesson +
                ", date=" + date +
                ", present=" + present +
                '}';
    }
}
