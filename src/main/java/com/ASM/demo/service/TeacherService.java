package com.ASM.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ASM.demo.model.Teacher;
import com.ASM.demo.model.Lesson;
import com.ASM.demo.model.Course;
import com.ASM.demo.repository.CourseRepository;
import com.ASM.demo.repository.LessonRepository;
import com.ASM.demo.repository.TeacherRepository;

import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;;

@Service
@Transactional
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private EntityManager entityManager;

    public List<Teacher> getTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        teacherRepository.findAll().forEach(teachers::add);
        return teachers;
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    public Iterable<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    public void deleteTeacher(Long teacherId) {

        List<Course> courses = courseRepository.findByInstructorId(teacherId);
        List<Lesson> lessons = lessonRepository.findByTeacherId(teacherId);
        courseRepository.deleteAll(courses);
        lessonRepository.deleteAll(lessons);
        teacherRepository.deleteById(teacherId);
    }

    public void addTeacher(Teacher teacher) {
        Teacher managedTeacher = entityManager.merge(teacher);
        teacherRepository.save(managedTeacher);
    }

    public void updateTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

}
