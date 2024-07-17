package com.ASM.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import com.ASM.demo.model.Course;
import com.ASM.demo.model.Teacher;
import com.ASM.demo.service.CourseService;
import com.ASM.demo.service.TeacherService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/courses")
    public String showCourseDepartmentPage() {
        return "Course-departement";
    }

    @GetMapping("courses/list")
    public String getCourses(Model model){
        List<Course> courses = courseService.getCourses();
        model.addAttribute("courses", courses);
        return "list-courses";

    }
    @GetMapping("/courses/find-by-id")
    public String showFindCourseForm(Model model) {
        model.addAttribute("courseId",null);
        return "find-course-by-id";
    }

    @PostMapping("/courses/find-by-id")
    public String findCourseById(@RequestParam Long id, Model model) {
        Course course =courseService.getCourseById(id);
        if(course==null){
            model.addAttribute("error", "course not found with the ID:"+id);
            return "find-course-by-id";

        }else{
            model.addAttribute("course", course);
            return "course-detail";
        }
        
    }

    @GetMapping("/courses/list/add-course")
    public String showAddCourseForm(Model model) {
        Course course = new Course();
        model.addAttribute("course", course);

        // Convert Iterable to List
        Iterable<Teacher> teachersIterable = teacherService.findAll();
        List<Teacher> teachers = new ArrayList<>();
        teachersIterable.forEach(teachers::add);

        model.addAttribute("teachers", teachers);
        return "add-course";
    }

    @PostMapping("/courses/list/add-course")
    public String saveCourse(@ModelAttribute("course") Course course, Model model) {
        try {
            courseService.addCourse(course);
            return "redirect:/courses/list";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "add-course"; // Render the add-course form with error message
        } catch (Exception ex) {
            ex.printStackTrace(); // Log the exception for debugging
            return "error"; // Redirect to a generic error page
        }
    }

    @GetMapping("/courses/list/edit/{id}")
    public String showEditCourseForm(@PathVariable Long id, Model model ){
        Course course =courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "edit-course";
    }

    @PostMapping("/courses/list/edit/{id}")
    public String updateCourse(@PathVariable Long id, @ModelAttribute Course course ){
        try{
            course.setId(id);
            courseService.updateCourse(course);
            return "redirect:/courses/list";
        }catch(Exception ex){
            ex.printStackTrace();
            return "error";
        }
    }


    @GetMapping("courses/list/delete/{courseId}")
    public String deleteCourse(@PathVariable Long courseId, RedirectAttributes redirectAttributes) {
        courseService.deleteCourse(courseId);
        
        // Add flash attribute to indicate successful deletion
        redirectAttributes.addFlashAttribute("message", "Course deleted successfully!");

        // Redirect to the courses list page
        return "redirect:/courses/list";
    }
}