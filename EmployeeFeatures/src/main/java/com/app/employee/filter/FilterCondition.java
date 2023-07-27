package com.app.employee.filter;

import com.app.employee.model.Employee;

public interface FilterCondition {
	boolean test(Employee employee);
}
