package com.ASM.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ASM.demo.model.Course;
import com.ASM.demo.model.Teacher;
import com.ASM.demo.repository.CourseRepository;

import com.ASM.demo.repository.TeacherRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TeacherRepository teacherRepository;



    public Iterable<Course> findAll(){
        return courseRepository.findAll();
    }

    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        courseRepository.findAll().forEach(courses::add);
        return courses;
    }
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public List<Course> getCoursesByInstructorId(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        // Fetch the course by ID
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        // Delete the course
        courseRepository.delete(course);
    }

    @Transactional
    public void addCourse(Course course) {
        try {
            Teacher instructor = course.getInstructor();
            if (instructor != null && instructor.getId() != null) {
                // Fetch the managed teacher entity
                Teacher managedTeacher = teacherRepository.findById(instructor.getId()).orElse(null);
                if (managedTeacher != null) {
                    // Set the managed teacher to the course
                    course.setInstructor(managedTeacher);
                    // Save the course
                    courseRepository.save(course);
                } else {
                    throw new IllegalArgumentException("Teacher with ID " + instructor.getId() + " not found");
                }
            } else {
                throw new IllegalArgumentException("Instructor ID cannot be null");
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle logging or further processing
            throw ex; // Rethrow the exception or handle differently based on your application's requirements
        }
    }

    @Transactional
    public void updateCourse(Course updatedCourse) {
        try {
            // Fetch the existing course from the database
            Course existingCourse = courseRepository.findById(updatedCourse.getId())
                                    .orElseThrow(() -> new IllegalArgumentException("Course with ID " + updatedCourse.getId() + " not found"));
    
            // Preserve the existing instructor
            Teacher existingInstructor = existingCourse.getInstructor();
    
            // Update properties of the existing course (excluding instructor)
            existingCourse.setCourseCode(updatedCourse.getCourseCode());
            existingCourse.setCourseName(updatedCourse.getCourseName());
            existingCourse.setCreditHours(updatedCourse.getCreditHours());
            existingCourse.setDepartment(updatedCourse.getDepartment());
    
            // Set the instructor from the existing course (to maintain its ID)
            existingCourse.setInstructor(existingInstructor);
    
            // Save the updated course
            courseRepository.save(existingCourse);
        } catch (IllegalArgumentException ex) {
            // Handle specific exception and possibly log it
            throw ex;
        } catch (Exception ex) {
            // Log the exception and handle it appropriately
            ex.printStackTrace(); // Example of logging
            throw new RuntimeException("Failed to update course: " + ex.getMessage(), ex);
        }
    }
}
