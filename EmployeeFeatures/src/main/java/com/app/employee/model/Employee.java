package com.app.employee.model;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Entity
@Table(name="empdb")
public class Employee {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="name")
	private String name;
	@Column(name="title")
	private String title;
	@Column(name="salary")
	private double salary;
	@Column(name="age")
	private int age;
	@Column(name="hiredate")
	private Date hireDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getHireDate() {
		return hireDate;
	}
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
	
	
	
	
	
	
	
	
	
	
	
//	public void setId(int int1) {
//		// TODO Auto-generated method stub
//		
//	}
//	public void setId(int id) {
//		// TODO Auto-generated method stub
//		this.id = id;
//	}
//	public void setName( String name) {
//		// TODO Auto-generated method stub
//		this.name = name;
//	}
//	public void setTitle( String title) {
//		// TODO Auto-generated method stub
//		this.title = title;
//		
//	}
//	public void setSalary( double salary) {
//		// TODO Auto-generated method stub
//		this.salary = salary;
//	}
//	public String getName() {
//		// TODO Auto-generated method stub
//		return name;
//	}
//	public String getTitle() {
//		// TODO Auto-generated method stub
//		return title;
//	}
//	public double getSalary() {
//		// TODO Auto-generated method stub
//		return salary;
//	}
//	public int getId() {
//		// TODO Auto-generated method stub
//		return id;
//	}


	
}
