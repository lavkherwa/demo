package com.example.demo.service;

import com.example.demo.data.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    public List<Student> getStudents();

    public Student getStudent(int id);

}
