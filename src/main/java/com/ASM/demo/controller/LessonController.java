package com.ASM.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ASM.demo.model.Course;
import com.ASM.demo.model.Lesson;
import com.ASM.demo.model.Teacher;
import com.ASM.demo.repository.CourseRepository;
import com.ASM.demo.service.CourseService;
import com.ASM.demo.service.LessonService;
import com.ASM.demo.service.TeacherService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LessonController {
    @Autowired
    private LessonService lessonService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/lessons")
    public String ShowLessonDepartment() {
        return "lesson-departement";
    }

    @GetMapping("/lessons/list")
    public String getLessons(Model model) {
        List<Lesson> lessons = lessonService.getLessons();
        model.addAttribute("lessons", lessons);
        return "list-lessons";
    }

    @GetMapping("lessons/find-by-id")
    public String FindLessonById(Model model) {
        model.addAttribute("lessonId", null);
        return "find-lesson-by-id";

    }

    @PostMapping("/lessons/find-by-id")
    public String findLessonById(@RequestParam Long id, Model model) {
        Lesson lesson = lessonService.getLessonById(id);
        if (lesson == null) {
            model.addAttribute("error", "lesson not found with the ID:" + id);
            return "find-lesson-by-id";

        } else {
            model.addAttribute("lesson", lesson);
            return "lesson-detail";
        }

    }

    @GetMapping("/lessons/list/add-lesson")
    public String showAddLessonForm(Model model) {
        Lesson lesson = new Lesson();
        model.addAttribute("lesson", lesson);

        // Fetch all teachers and courses from the database
        Iterable<Teacher> teachersIterable = teacherService.findAll();
        List<Teacher> teachers = new ArrayList<>();
        teachersIterable.forEach(teachers::add);

        Iterable<Course> coursesIterable = courseService.findAll();
        List<Course> courses = new ArrayList<>();
        coursesIterable.forEach(courses::add);

        model.addAttribute("teachers", teachers);
        model.addAttribute("courses", courses);

        return "add-lesson";
    }

    @PostMapping("/lessons/list/add-lesson")
    public String saveLesson(@ModelAttribute("lesson") Lesson lesson, Model model) {
        try {
            // Fetch course and its instructor from the repository
            Long courseId = lesson.getCourse().getId();
            Course managedCourse = courseRepository.findById(courseId)
                    .orElseThrow(() -> new IllegalArgumentException("Course with ID " + courseId + " not found"));

            // Assign the course instructor to the lesson's teacher
            Teacher courseInstructor = managedCourse.getInstructor();
            if (courseInstructor == null) {
                throw new IllegalArgumentException("Course with ID " + courseId + " has no instructor assigned");
            }
            lesson.setTeacher(courseInstructor);

            // Set the course in the lesson
            lesson.setCourse(managedCourse);

            // Save the lesson using the service
            lessonService.addLesson(lesson);

            return "redirect:/lessons/list";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "add-lesson"; // Render the add-lesson form with error message
        } catch (Exception ex) {
            ex.printStackTrace(); // Log the exception for debugging
            return "error"; // Redirect to a generic error page
        }
    }

    @GetMapping("/lessons/list/edit/{id}")
    public String showEditlessonForm(@PathVariable Long id, Model model) {
        Lesson lesson = lessonService.getLessonById(id);
        model.addAttribute("lesson", lesson);
        return "edit-lesson";
    }

    @PostMapping("/lessons/list/edit/{id}")
    public String updateLesson(@ModelAttribute("lesson") Lesson updatedLesson, Model model) {
        try {
            Long ID = updatedLesson.getId();
            Lesson lessontoup = lessonService.getLessonById(ID);
            lessonService.updateLesson(lessontoup);
            return "redirect:/lessons/list";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "error-page"; // Redirect to an error page or handle appropriately
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("error", "Failed to update lesson.");
            return "error-page"; // Redirect to an error page or handle appropriately
        }
    }

    @GetMapping("/lessons/list/delete/{id}")
    public String deleteLesson(@PathVariable Long id) {
        try {
            lessonService.deleteLesson(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "redirect:/lessons/list";
    }
}
