package com.ASM.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

import com.ASM.demo.model.Teacher;
import com.ASM.demo.service.TeacherService;

import java.util.List;

@Controller
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/Teachers")
    public String showTeacherDepartmentPage() {
        return "Teacher-department";

    }

    @GetMapping("/Teachers/list")
    public String getTeachers(Model model) {
        List<Teacher> teachers = teacherService.getTeachers();
        model.addAttribute("teachers", teachers);
        return "list-teachers";
    }

    @GetMapping("/Teachers/find-by-id")
    public String showFindStudentForm(Model model) {
        model.addAttribute("teacherId", null); // No need for a separate form object
        return "find-teacher-by-id";
    }

    @PostMapping("Teachers/find-by-id")
    public String getTeacherById(@RequestParam Long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id);
        if (teacher == null) {
            model.addAttribute("error", "teacher not found with the ID:" + id);
            return "find-teacher-by-id";
        } else {
            model.addAttribute("teacher", teacher);
            return "teacher-detail";
        }
    }

    @GetMapping("/Teachers/list/add-teacher")
    public String showAddTeacherForm(Model model) {
        Teacher teacher = new Teacher();
        model.addAttribute("teacher", teacher);
        return "Add-teacher";
    }

    @PostMapping("/Teachers/list/add-teacher")
    public String saveTeacher(@ModelAttribute("teacher") Teacher teacher) {
        try {
            teacherService.addTeacher(teacher);
            return "redirect:/Teachers/list";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/Teachers/list/edit/{id}")
    public String showEditStudentForm(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacher);
        return "Edit-teacher";
    }

    @PostMapping("/Teachers/list/edit/{id}")
    public String updateTeacher(@PathVariable Long id, @ModelAttribute Teacher teacher) {

        try {
            teacherService.updateTeacher(teacher);
            return "redirect:/Teachers/list";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/Teachers/list/Delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {

        try {
            teacherService.deleteTeacher(id);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "redirect:/Teachers/list";
    }
}
