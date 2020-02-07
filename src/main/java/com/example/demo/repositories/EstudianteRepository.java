package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Estudiante;

@Repository
//public interface EstudianteRepository extends MongoRepository<Estudiante, Long>{
public interface EstudianteRepository extends CrudRepository<Estudiante, Long>{
	List<Estudiante> findAll();
	
}
