package com.ASM.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ASM.demo.model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
}
