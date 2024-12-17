package com.student.portal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.portal.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	Student findByName(String name);

}
