package fr.red.services.department.port.application;

import fr.red.services.department.model.Department;

import java.util.List;

public interface DepartmentRequester {

    List<Department> getDepartments();

    Department getDepartmentById(String deptno);

    Department addDepartment(Department department);

}
