package com.ASM.demo.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ASM.demo.model.Attendance;
import com.ASM.demo.model.Course;
import com.ASM.demo.model.Lesson;
import com.ASM.demo.model.Teacher;
import com.ASM.demo.repository.AttendanceRepository;
import com.ASM.demo.repository.CourseRepository;
import com.ASM.demo.repository.LessonRepository;


import jakarta.transaction.Transactional;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Lesson> getLessons() {
        List<Lesson> lessons = new ArrayList<>();
        lessonRepository.findAll().forEach(lessons::add);
        return lessons;
    }

    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id).orElse(null);
    }

    public List<Lesson> getCourseByTeacherId(Long teacherId) {
        return lessonRepository.findByTeacherId(teacherId);
    }

    @Transactional
    public void deleteLesson(Long lessonId) {
        // Supprimer les présences associées à la leçon
        List<Attendance> attendances = attendanceRepository.findByLessonId(lessonId);
        for (Attendance attendance : attendances) {
            attendanceRepository.delete(attendance);
        }

        // Maintenant, supprimer la leçon
        lessonRepository.deleteById(lessonId);
    }

    @Transactional
    public void addLesson(Lesson lesson) {
        try {
            // Fetch and set the existing course
            Course course = lesson.getCourse();
            if (course != null && course.getId() != null) {
                Course managedCourse = courseRepository.findById(course.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Course with ID " + course.getId() + " not found"));
                
                // Automatically assign the teacher from the course to the lesson
                Teacher courseInstructor = managedCourse.getInstructor();
                if (courseInstructor != null) {
                    lesson.setCourse(managedCourse);
                    lesson.setTeacher(courseInstructor);
                } else {
                    throw new IllegalArgumentException("Course with ID " + course.getId() + " has no instructor assigned");
                }
            } else {
                throw new IllegalArgumentException("Course ID cannot be null");
            }
    
            // Save the lesson
            lessonRepository.save(lesson);
    
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle logging or further processing
            throw ex; // Rethrow the exception or handle differently based on your application's requirements
        }
    }

    @Transactional
    public void updateLesson(Lesson updatedLesson) {
        try {
            // Fetch the existing lesson from the database
            Lesson existingLesson = lessonRepository.findById(updatedLesson.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Lesson with ID " + updatedLesson.getId() + " not found"));
    
            // Update properties of the existing lesson
            existingLesson.setStartTime(updatedLesson.getStartTime());
            existingLesson.setEndTime(updatedLesson.getEndTime());
    
            // Save the updated lesson
            lessonRepository.save(existingLesson);
        } catch (IllegalArgumentException ex) {
            // Handle specific exception and possibly log it
            throw ex;
        } catch (Exception ex) {
            // Log the exception and handle it appropriately
            ex.printStackTrace(); // Example of logging
            throw new RuntimeException("Failed to update lesson: " + ex.getMessage(), ex);
        }
    }
}
