package com.ASM.demo.repository;

import java.util.*;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ASM.demo.model.Attendance;

@Repository
public interface AttendanceRepository extends CrudRepository<Attendance, Long> {
    List<Attendance> findByStudentId(Long studentId);
    List<Attendance>findByLessonId(Long lessonId);

}
