package com.ASM.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.ASM.demo.model.Student;
import com.ASM.demo.service.StudentService;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;
    


    @GetMapping("/")
    public String showDepartments() {
        return "index";
    }

    @GetMapping("/students")
    public String showStudentDepartmentPage() {
        return "student-department";
    }


    @GetMapping("/students/list")
    public String getStudents(Model model) {
        List<Student> students = studentService.getStudents();
        model.addAttribute("students", students);
        return "list-students"; 
    }

    @GetMapping("/students/find-by-id")
    public String showFindStudentForm(Model model) {
        model.addAttribute("studentId", null); 
        return "find-student-by-id";
    }
    
   
    @PostMapping("/students/find-by-id")
    public String findStudentById(@RequestParam Long id, Model model) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            model.addAttribute("error", "Student not found with ID: " + id);
            return "find-student-by-id";
        } else {
            model.addAttribute("student", student);
            return "student-detail";
        }
    }


    
    

    @GetMapping("/students/list/add-student")
    public String showAddStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "add-estudiante"; 
    }


    @PostMapping("/students/list/add-student")
    public String saveStudent(@ModelAttribute("student") Student student) {
        try {
            studentService.AddStudent(student);
            return "redirect:/students/list"; 
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }


    @GetMapping("/students/list/edit/{id}")
    public String showEditStudentForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "edit-student"; 
    }

    @PostMapping("/students/list/edit/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student student) {
        try {
            studentService.updateStudent(student, id);
            return "redirect:/students/list"; 
        } catch (Exception ex) {
            ex.printStackTrace(); 
            return "error"; 
        }
    }





    @GetMapping("/students/list/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudentById(id);
            
        } catch (Exception e) {
            
            e.printStackTrace(); 
            throw e;
        }
        return "redirect:/students/list"; 
    }
}
