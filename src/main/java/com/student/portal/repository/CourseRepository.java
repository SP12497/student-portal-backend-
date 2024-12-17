package com.student.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.portal.model.Course;
import com.student.portal.model.Student;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	
	Course findByTitle(String title);

}
