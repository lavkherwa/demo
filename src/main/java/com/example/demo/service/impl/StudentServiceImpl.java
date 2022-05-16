package com.example.demo.service.impl;

import com.example.demo.data.Student;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepo;

    @Override
    public List<Student> getStudents() {

        List<Student> students = (List<Student>) studentRepo.findAll();
        if (!students.isEmpty())
            return students;

        throw new DataNotFoundException("No students found");
    }

    @Override
    public Student getStudent(int id) {

        if (studentRepo.findById(id).isPresent())
            return studentRepo.findById(id).get();

        throw new DataNotFoundException(String.format("Student not found with ID: %d", id));
    }

}
