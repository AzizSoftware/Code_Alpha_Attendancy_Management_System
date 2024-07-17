package com.ASM.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ASM.demo.model.Attendance;
import com.ASM.demo.model.Student;
import com.ASM.demo.repository.AttendanceRepository;
import com.ASM.demo.repository.StudentRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EntityManager entityManager; //

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(student -> {
            students.add(student);
        });
        return students;
    }

    public Student getStudentById(Long id) {

        return studentRepository.findById(id).orElse(null);
    }
    @Transactional
    public void deleteStudentById(Long studentId) {
        List<Attendance> attendances = attendanceRepository.findByStudentId(studentId);
        attendanceRepository.deleteAll(attendances);
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void AddStudent(Student student) {
        Student managedStudent = entityManager.merge(student);
        studentRepository.save(managedStudent);
    }

    public void updateStudent(Student studentt, Long id) {
        studentRepository.save(studentt);

    }
}
