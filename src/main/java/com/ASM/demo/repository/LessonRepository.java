package com.ASM.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ASM.demo.model.Lesson;
import java.util.*;

@Repository
public interface LessonRepository extends CrudRepository<Lesson, Long> {

    List<Lesson> findByTeacherId(Long teacherId);
    List<Lesson>findByCourseId(Long courseId);
}
