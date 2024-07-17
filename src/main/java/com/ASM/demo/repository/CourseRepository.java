package com.ASM.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.ASM.demo.model.Course;
import java.util.*;

@Repository
public interface CourseRepository  extends CrudRepository<Course, Long>  {
    List<Course> findByInstructorId(Long instructorId);
}