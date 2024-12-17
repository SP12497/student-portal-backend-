package com.student.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.student.portal.model.Course;
import com.student.portal.model.Student;
import com.student.portal.repository.CourseRepository;
import com.student.portal.repository.StudentRepository;
import com.student.portal.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	public List<Course> getCoursesByStudentId(Long studentId) {
		return studentRepository.findById(studentId).map(Student::getCourses)
				.orElseThrow(() -> new RuntimeException("Student not found"));
	}

	
	@Transactional
	public Student createStudentWithCourses(Student student) {
		List<Course> existingCourses = student.getCourses().stream().map(course -> {
			Course existingCourse = courseRepository.findByTitle(course.getTitle());

			if (existingCourse == null) {
				existingCourse = new Course();
				existingCourse.setTitle(course.getTitle());
				existingCourse = courseRepository.save(existingCourse);
			}

			return existingCourse;
		}).collect(Collectors.toList());

		student.setCourses(existingCourses);

		if (student.getId() != null) {
			Student existingStudent = studentRepository.findById(student.getId()).orElse(null);

			if (existingStudent != null) {
				existingStudent.setCourses(existingCourses);
				return studentRepository.save(existingStudent);
			}
		}
		return studentRepository.save(student);

	}
}
