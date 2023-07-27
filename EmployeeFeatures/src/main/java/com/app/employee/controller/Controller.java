package com.app.employee.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.employee.exception.EmployeeNotFoundException;
import com.app.employee.model.Employee;
import com.app.employee.serviceimpl.ServiceImpl;
//import com.employee.management.model.Employee;

@RestController
public class Controller {

//	@Autowired(required=true)
	private ServiceImpl service;

	private final Logger logger = LoggerFactory.getLogger(ServiceImpl.class);

	@Autowired(required = true)
	public Controller(ServiceImpl service) {
		this.service = service;
	}

	@GetMapping("/employee")
	 public ResponseEntity<List<Employee>> getAllEmployees(
	            @RequestParam(defaultValue = "id,asc") String[] sort
	    ) {
	        Sort.Direction sortDirection = Sort.Direction.ASC;
	        if (sort.length > 1 && sort[1].equalsIgnoreCase("desc")) {
	            sortDirection = Sort.Direction.DESC;
	        }
	        Sort sortBy = Sort.by(sortDirection, sort[0]);
	        List<Employee> employees = service.getAllEmployees(sortBy);
	        return new ResponseEntity<>(employees, HttpStatus.OK);
	    }
	
	@GetMapping("/page")
    public ResponseEntity<Page<Employee>> getAllEmployeesWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (sort.length > 1 && sort[1].equalsIgnoreCase("desc")) {
            sortDirection = Sort.Direction.DESC;
        }
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort[0]));
        Page<Employee> employeesPage = service.getAllEmployeesWithPagination(pageable);
        return new ResponseEntity<>(employeesPage, HttpStatus.OK);
    }

	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) throws EmployeeNotFoundException {
		Employee employee = service.getEmployeeById(id);
		if (employee != null) {
			return ResponseEntity.ok().body(employee);
		} else {
			return ResponseEntity.badRequest().build();
		}

	}

	@PostMapping("/Employee")
	public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
		try {
			service.addEmployee(employee);
			return ResponseEntity.ok().body("Employee addedd sccessfully");
		} catch (EmployeeNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Existing Employee cannot be added");

		}

	}

	@PatchMapping("/update/{id}")
	public ResponseEntity<Object> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
//		service.setId(id);
		Employee employees = service.updateEmployee(id, employee);
		if (employees != null) {
			service.updateEmployee(id, employees);
			return ResponseEntity.ok().body("Employee updated successfully with id :" + id);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee not updated");
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable int id) {
		boolean isDeleted = service.deleteEmployee(id);
		if (!isDeleted) {
			return ResponseEntity.ok().body("Employee deleted successfully with id :" + id);
		} else {
			return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Employee not deleted");
		}

	}

	@GetMapping("/aboveAge/{age}")
	public ResponseEntity<List<Employee>> getEmployeesAboveAge(@PathVariable int age) {
		List<Employee> employeeAboveAge = service.filterEmployee(employee -> employee.getAge() > age);
		if (employeeAboveAge.isEmpty()) {
			return ResponseEntity.ok().body(new ArrayList<>());
		} else {
			return ResponseEntity.ok().body(employeeAboveAge);
		}
	}

	@GetMapping("/currentDate")
	public LocalDate getCurrentDate() {
		return service.getCurrentDate();
	}

	@GetMapping("/daysBetween")
	public long daysBetweenDates(
			@RequestParam("startdate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startdate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return service.daysBetweenDates(startdate, endDate);
	}
}
