package com.example.demo.service.impl;

import com.example.demo.exception.DataNotFoundException;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StudentServiceImplTest {

    @Test
    @DisplayName("Test should throw exception when no students found")
    void shouldFailWhenNoAuthorsFound() {
        StudentRepository studentRepo = Mockito.mock(StudentRepository.class);
        StudentService studentService = new StudentServiceImpl(studentRepo);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, studentService::getStudents);

        assertThat(exception.getMessage().equals("No students found")).isTrue();

    }

    @Test
    @DisplayName("Test should throw exception when no student is found")
    void shouldFailWhenNoAuthorFound() {
        StudentRepository studentRepo = Mockito.mock(StudentRepository.class);
        StudentService studentService = new StudentServiceImpl(studentRepo);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () ->
                studentService.getStudent(1)
        );

        assertThat(exception.getMessage().equals("Student not found with ID: 1")).isTrue();
    }
}