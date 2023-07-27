package com.app.employee.serviceimpl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.app.employee.exception.EmployeeNotFoundException;
import com.app.employee.filter.FilterCondition;
import com.app.employee.model.Employee;
import com.app.employee.repository.Repository;
import com.app.employee.service.Service;
//import com.employee.management.repository.Repository;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

	private Repository repository;

	private final List<Employee> employees = new ArrayList<>();

	@Autowired(required = true)
	public ServiceImpl(Repository repository) {
		this.repository = repository;
	}

	@Override
	 public List<Employee> getAllEmployees(Sort sort) {
        return repository.findAll(sort);
    }
	@Override
	public Employee getEmployeeById(int id) throws EmployeeNotFoundException {
		// TODO Auto-generated method stub
		return repository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id :" + id));
	}

	@Override
	public Employee addEmployee(Employee employee) {
		// TODO Auto-generated method stub
		validateEmployeeFields(employee);
		Employee employees = repository.findById(employee.getId()).orElse(null);
		if (employees != null) {
			throw new EmployeeNotFoundException("Employee with id already exists");
		}
		return this.repository.save(employee);

	}

	private void validateEmployeeFields(Employee employee) {
		// TODO Auto-generated method stub
		if (employee.getName().isEmpty() || employee.getName() == null) {
			throw new EmployeeNotFoundException("Employee name is mandatory");
		}
		if (employee.getTitle().isEmpty() || employee.getTitle() == null) {
			throw new EmployeeNotFoundException("Employee title is mandatory");
		}
		if (employee.getAge() <= 18) {
			throw new EmployeeNotFoundException("Employee age must be greater than 18 ");
		}
		if (employee.getSalary() < 0) {
			throw new EmployeeNotFoundException("Employee salary cannot be negative");
		}

	}

	@Override
	public Employee updateEmployee(int id, Employee updatedEmployee) {
		repository.findById(id).ifPresent(existingEmployee -> {
			existingEmployee.setName(updatedEmployee.getName());
			existingEmployee.setTitle(updatedEmployee.getTitle());
			existingEmployee.setSalary(updatedEmployee.getSalary());
			repository.save(existingEmployee);
		});
		return updatedEmployee;
	}

	public List<Employee> filterEmployee(FilterCondition filterCondition) {
		return repository.findAll().stream().filter(filterCondition::test).collect(Collectors.toList());
	}

	@Override
	public boolean deleteEmployee(int id) {
		// TODO Auto-generated method stub
		this.repository.deleteById(id);
		return false;
	}

	@Override
	public List<Employee> getEmployeesWithAgeAbove(int age) {
		return repository.findAll().stream().filter(employee -> employee.getAge() > age).collect(Collectors.toList());
	}

	@Override
	public LocalDate getCurrentDate() {
		return LocalDate.now();
	}

	@Override
	public long daysBetweenDates(LocalDate startdate, LocalDate endDate) {
		return ChronoUnit.DAYS.between(startdate, endDate);
	}

	@Override
	public Page<Employee> getAllEmployeesWithPagination(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public boolean findPKey(int id) {
		boolean employee_exist;
		employee_exist=repository.existsById(id);
		return employee_exist;
	}

}


