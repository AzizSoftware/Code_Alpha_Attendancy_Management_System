package com.ASM.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ASM.demo.model.Attendance;
import com.ASM.demo.repository.AttendanceRepository;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance> getAttendances() {
        List<Attendance> attendances = new ArrayList<>();
        attendanceRepository.findAll().forEach(attendances::add);
        return attendances;
    }

    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id).orElse(null);
    }

    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

    public void addAttendance(Attendance attendance) {
        attendanceRepository.save(attendance);
    }

    public void updateAttendance(Attendance attendance, Long id) {
        attendance.setId(id); // Ensure the ID is set
        attendanceRepository.save(attendance);
    }
}
