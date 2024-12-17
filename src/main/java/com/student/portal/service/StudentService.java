package com.student.portal.service;

import java.util.List;

import com.student.portal.model.Course;
import com.student.portal.model.Student;

public interface StudentService {
	
	public List<Student> getAllStudents();
	
	public List<Course> getCoursesByStudentId(Long studentId);

	public Student createStudentWithCourses(Student student);

}
