package com.ASM.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ASM.demo.model.Attendance;
import com.ASM.demo.service.AttendanceService;

import java.util.List;

@RestController
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @RequestMapping(method = RequestMethod.GET, value = "/attendances")
    public List<Attendance> getAttendances() {
        return attendanceService.getAttendances();
    }

    @RequestMapping("/attendance/{id}")
    public Attendance getAttendanceById(@PathVariable Long id) {
        return attendanceService.getAttendanceById(id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/attendance/{id}")
    public void removeAttendanceById(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/attendances")
    public void addAttendance(@RequestBody Attendance attendance) {
        attendanceService.addAttendance(attendance);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/attendance/{id}")
    public void updateAttendance(@RequestBody Attendance attendance, @PathVariable Long id) {
        attendanceService.updateAttendance(attendance, id);
    }
}
