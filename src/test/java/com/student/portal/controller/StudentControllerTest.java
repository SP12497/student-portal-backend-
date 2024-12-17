package com.student.portal.controller;

import com.student.portal.model.Course;
import com.student.portal.model.Student;
import com.student.portal.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        Course course = new Course();
        course.setTitle("Java");

        student = new Student();
        student.setId(1L);
        student.setName("Sagar");
        student.setCourses(Arrays.asList(course));
    }

    @Test
    void testGetAllStudents() throws Exception {
        List<Student> students = Arrays.asList(student);
        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Sagar"))
                .andExpect(jsonPath("$[0].courses[0].title").value("Java"));
    }

    @Test
    void testGetCoursesByStudentId() throws Exception {
        when(studentService.getCoursesByStudentId(1L)).thenReturn(student.getCourses());

        mockMvc.perform(get("/api/students/1/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Java"));
    }

    @Test
    void testCreateStudentWithCourses() throws Exception {
        when(studentService.createStudentWithCourses(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Sagar\", \"courses\": [{\"title\": \"Java\"}]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Sagar"))
                .andExpect(jsonPath("$.courses[0].title").value("Java"));
    }
}
