package com.ASM.demo.controller;

import com.ASM.demo.model.Report;
import com.ASM.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/reports")
    public String showReportDepartment() {
        return "report-department";
    }

    @PostMapping("reports/generate")
    public String generateReport(@RequestParam Long studentId, Model model) {
        Report report = reportService.generateStudentReport(studentId);
        if (report == null) {
            model.addAttribute("error", "No report found for student ID " + studentId);
        } else {
            model.addAttribute("report", report);
        }
        return "report-department";
    }
}
