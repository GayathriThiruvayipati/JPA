package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Employee;
import com.example.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/practice/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	// get employees
	@GetMapping("employees")
	public List<Employee> getAllEmployees() {
		return this.employeeRepository.findAll();
	}

	// get employees by ID
	@GetMapping("employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not found by this id :" + employeeId));

		return ResponseEntity.ok().body(employee);
	}

	// save/add employees
	@PostMapping("addemployee")
	public Employee addEmployee(@RequestBody Employee employee) {
		return this.employeeRepository.save(employee);
	}

	// update employees
	@PutMapping("updateemployee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") long employeeId,
			@Valid @RequestBody Employee employeedetails) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not found by this id :" + employeeId));

		employee.setFirstName(employeedetails.getFirstName());
		employee.setLastName(employeedetails.getLastName());
		employee.setEmail(employeedetails.getEmail());

		return ResponseEntity.ok(this.employeeRepository.save(employee));
	}

	// delete employees
	@DeleteMapping("deleteemployee/{id}")
	public Map<String,Boolean> deleteEmployee(@PathVariable(value="id") long employeeId) 
			throws ResourceNotFoundException {
		Employee employee= employeeRepository.findById(employeeId)
		.orElseThrow(() -> new ResourceNotFoundException("Employee Not found by this id :"+ employeeId));
		
		this.employeeRepository.deleteById(employeeId);
		
		Map<String,Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);

		return response;
		}

}
