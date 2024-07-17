package com.ASM.demo.service;

import com.ASM.demo.model.Report;
import com.ASM.demo.model.Student;
import com.ASM.demo.model.Lesson;
import com.ASM.demo.model.Attendance;
import com.ASM.demo.repository.StudentRepository;
import com.ASM.demo.repository.LessonRepository;
import com.ASM.demo.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ReportService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    public Report generateStudentReport(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return null; // or throw an exception
        }
        List<Lesson> lessons = StreamSupport.stream(lessonRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        List<Attendance> attendances = attendanceRepository.findByStudentId(studentId);

        int totalLessons = lessons.size();
        int attendedLessons = (int) attendances.stream().filter(Attendance::isPresent).count();
        double attendancePercentage = (double) attendedLessons / totalLessons * 100;

        Report report = new Report();
        report.setStudent(student);
        report.setCourse(null); // or set the relevant course
        report.setTotalLessons(totalLessons);
        report.setAttendedLessons(attendedLessons);
        report.setAttendancePercentage(attendancePercentage);

        return report;
    }
}