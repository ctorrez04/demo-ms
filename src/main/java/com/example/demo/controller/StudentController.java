package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.Student;
import com.example.demo.service.StudentService;

@RestController
@RequestMapping("students")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	private static final String URL_ZUUL_SERVER="http://zuul-server";
	private static final String URL_EXTERNAL_SERVICE="http://external-service";
	
	@Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Student> getAllStudents() {
		return studentService.getAllStudents();
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Student> createStudent(@RequestBody Student body) {
		Student res = studentService.createStudent(body);
		if (res == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Student> updateStudent(@RequestBody Student body, @PathVariable Long id) {
		System.out.println("un id " + id);
		body.setId(id); // Student
		Student res = studentService.updateStudent(body);
		if (res == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Student> getStudent(@PathVariable Long id) {
		Student res = studentService.getStudent(id);
		if (res == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
		Student res = studentService.getStudent(id);
		if (res == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		studentService.deleteStudent(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "/zuul/", method = RequestMethod.GET)
	public ResponseEntity<Object> getStudentList() {
		ResponseEntity<Object[]> res= restTemplate.getForEntity(
				URL_ZUUL_SERVER + "/demo/students", Object[].class);
		return new ResponseEntity<>(res.getBody(), HttpStatus.ACCEPTED);
	}
	@RequestMapping(value = "/external/", method = RequestMethod.GET)
	public ResponseEntity<Object> getStudentListByExt() {
		ResponseEntity<Object[]> res= restTemplate.getForEntity(
				URL_EXTERNAL_SERVICE + "/", Object[].class);
		return new ResponseEntity<>(res.getBody(), HttpStatus.ACCEPTED);
	}
	@RequestMapping(value = "/external/hello", method = RequestMethod.GET)
	public String getHello() {
		String res = restTemplate.getForObject(
				URL_ZUUL_SERVER + "/hello", String.class);
		return res;
	}
}
