package com.glca.emp.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.glca.emp.entity.Employee;
import com.glca.emp.entity.Role;
import com.glca.emp.entity.User;
import com.glca.emp.service.IEmployeeService;
import com.glca.emp.service.IRoleService;
import com.glca.emp.service.IUserService;

@RestController
@RequestMapping("/emp")
public class EmployeeRestController {

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@PostMapping("/add-employee")
	public Employee addEmployee(@RequestBody Employee employee) {
		// employee.setId(0);
		System.out.println("employ to save:" + employee);
		return employeeService.addEmployee(employee);
	}

	@PutMapping("/update-employee")
	public Employee updateEmployee(@RequestBody Employee employee) {
		return employeeService.updateEmployee(employee);
	}

	@GetMapping("/get-employees")
	public List<Employee> getAllEmployees() {

		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		System.out.println("authorities:" + authorities);
		return employeeService.getAllEmployees();
	}

	@GetMapping("/get-employee-byid/{employeeId}")
	public Employee getEmployeeById(@PathVariable("employeeId") long id) {
		return employeeService.getEmployeeById(id);
	}

	@DeleteMapping("/delete-employee/{employeeId}")
	public String deleteEmployee(@PathVariable("employeeId") long id) {
		if (employeeService.deleteEmployee(id)) {
			return "Deleted employee id - " + id;
		}
		return "Employee details not deleted ";
	}

	@GetMapping("/search-employee/{firstName}")
	public List<Employee> searchByFirstName(@PathVariable("firstName") String firstName) {
		return employeeService.searchByFirstName(firstName);
	}

	@GetMapping("/sort-asc")
	public List<Employee> sortByFirstNameAsc() {
		return employeeService.sortByFirstNameAsc();
	}

	@GetMapping("/sort-desc")
	public List<Employee> sortByFirstNameDesc() {
		return employeeService.sortByFirstNameDesc();
	}

	@PostMapping("/add-user")
	public User addUser(@RequestBody User user) {
		return userService.addUser(user);
	}

	@PostMapping("/add-role")
	public Role addRole(@RequestBody Role role) {
		return roleService.addRole(role);
	}

}