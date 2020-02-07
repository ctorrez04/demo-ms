package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.Student;

public interface StudentService {
	
	List<Student> getAllStudents();
	Student getStudent(Long id);
	Student createStudent(Student data);
	Student updateStudent(Student data);
	void deleteStudent(Long id);

}
