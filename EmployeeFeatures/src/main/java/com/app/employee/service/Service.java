package com.app.employee.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.app.employee.exception.EmployeeNotFoundException;
import com.app.employee.model.Employee;

@org.springframework.stereotype.Service
public interface Service {

	public List<Employee> getAllEmployees(Sort sort);

	Employee getEmployeeById(int id) throws EmployeeNotFoundException;

	Employee addEmployee(Employee employee);

	Employee updateEmployee(int id, Employee employee);

	boolean deleteEmployee(int id);

	public List<Employee> getEmployeesWithAgeAbove(int age);

	public LocalDate getCurrentDate();

	public long daysBetweenDates(LocalDate startdate, LocalDate endDate);

//	Page<Employee> getAllEmployees(Pageable pageable);

	public Page<Employee> getAllEmployeesWithPagination(Pageable pageable);
	
	boolean findPKey(int id);


}
