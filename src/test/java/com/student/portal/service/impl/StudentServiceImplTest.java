package com.student.portal.service.impl;

import com.student.portal.model.Course;
import com.student.portal.model.Student;
import com.student.portal.repository.CourseRepository;
import com.student.portal.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    private Student student;
    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setTitle("Java");

        student = new Student();
        student.setId(1L);
        student.setName("Sagar");
        student.setCourses(Arrays.asList(course));
    }

    @Test
    void testGetAllStudents() {
        List<Student> students = Arrays.asList(student);
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sagar", result.get(0).getName());
    }

    @Test
    void testGetCoursesByStudentId() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        List<Course> courses = studentService.getCoursesByStudentId(1L);

        assertNotNull(courses);
        assertEquals(1, courses.size());
        assertEquals("Java", courses.get(0).getTitle());
    }

    @Test
    void testCreateStudentWithCourses() {
        when(courseRepository.findByTitle(anyString())).thenReturn(course);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student createdStudent = studentService.createStudentWithCourses(student);

        assertNotNull(createdStudent);
        assertEquals("Sagar", createdStudent.getName());
        assertEquals(1, createdStudent.getCourses().size());
        assertEquals("Java", createdStudent.getCourses().get(0).getTitle());
    }
}
