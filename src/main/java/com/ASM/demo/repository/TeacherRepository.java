package com.ASM.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ASM.demo.model.Teacher;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {

}
