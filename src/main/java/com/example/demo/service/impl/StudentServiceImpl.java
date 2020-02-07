package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.Student;
import com.example.demo.model.Estudiante;
import com.example.demo.repositories.EstudianteRepository;
import com.example.demo.service.StudentService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private EstudianteRepository estudianteRepo;
	
	private Student parseToDTO(Estudiante reg) {
		Student s = new Student();
		s.setName(reg.getName());
		s.setGrade(reg.getGrade());
		s.setId(reg.getId());
		return s;
	}
	public List<Student> getAllStudents(){
		List<Student> res = new ArrayList<>();
		List<Estudiante> data = estudianteRepo.findAll();
		System.out.print("list size "+ data.size());
		for(Estudiante reg : data) {
			res.add(parseToDTO(reg));
		}
		return res;
	}
	public Student getStudent(Long id) {
		Optional<Estudiante> est = estudianteRepo.findById(id);
		return est.isPresent() ? parseToDTO(est.get()): null;
	}
	public Student createStudent(Student data) {
		Estudiante est = new Estudiante();
		est.setName(data.getName());
		est.setGrade(data.getGrade());
		//est.setId(data.getId());
		return parseToDTO(estudianteRepo.save(est));
	}
	public Student updateStudent(Student data) {
		Optional<Estudiante> optEst = estudianteRepo.findById(data.getId());
		if(!optEst.isPresent()) return null;
		Estudiante est = optEst.get();
		est.setName(data.getName());
		est.setGrade(data.getGrade());
		return parseToDTO(estudianteRepo.save(est));
	}
	public void deleteStudent(Long id) {
		estudianteRepo.deleteById(id);
	}

}
