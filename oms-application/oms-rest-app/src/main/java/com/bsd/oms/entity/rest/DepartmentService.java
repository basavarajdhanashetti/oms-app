package com.bsd.oms.entity.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bsd.oms.entity.Department;
import com.bsd.oms.repo.DepartmentRepository;

@RestController
@RequestMapping(path = "/departments")
public class DepartmentService {

	private static Logger LOG = LoggerFactory.getLogger(DepartmentService.class);

	@Autowired
	private DepartmentRepository departmentRepo;

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createDepartment(@RequestBody Department department) {
		LOG.debug("Create new department with (" + department.toString() + " )");
		System.out.println("Create new department with (" + department.toString() + " )");
		department = departmentRepo.save(department);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(department.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * 
	 * @param order
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<List<Department>> getAllDepartment() {
		LOG.debug("Get All department ");
		System.out.println("Get All department ");
		List<Department> lst = new ArrayList<Department>();
		for (Department department : departmentRepo.findAll()) {
			lst.add(department);
		}
		return ResponseEntity.ok(lst);
	}

	/**
	 * 
	 * @param department
	 * @return
	 */
	@PutMapping(path = "/{departmentId}", consumes = "application/json")
	public ResponseEntity<Department> updateDepartment(@PathVariable long departmentId, @RequestBody Department department) {
		LOG.debug("Update department with (" + department.toString() + " )");

		department.setId(departmentId);
		department = departmentRepo.save(department);

		return ResponseEntity.ok(department);
	}

	/**
	 * 
	 * @param department
	 * @return
	 */
	@GetMapping(path = "/{departmentId}")
	public ResponseEntity<Department> getDepartment(@PathVariable long departmentId) {
		LOG.debug("Get department for id " + departmentId);

		Department department = departmentRepo.findOne(departmentId);

		return ResponseEntity.ok(department);
	}

	/**
	 * 
	 * @param department
	 * @return
	 */
	@DeleteMapping(path = "/{departmentId}")
	public ResponseEntity<?> deleteDepartment(@PathVariable long departmentId) {
		LOG.debug("delete department for id " + departmentId);

		departmentRepo.delete(departmentId);

		return ResponseEntity.noContent().build();
	}

}
