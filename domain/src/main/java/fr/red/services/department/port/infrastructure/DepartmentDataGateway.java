package fr.red.services.department.port.infrastructure;

import fr.red.services.department.model.Department;

import java.util.List;

public interface DepartmentDataGateway {

    List<Department> getDepartments();

    Department getDepartmentById(String deptno);

    Department addDepartment(Department department);
}
