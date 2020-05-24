package fr.red.services.department.service;

import fr.red.services.department.model.Department;
import fr.red.services.department.port.application.DepartmentRequester;
import fr.red.services.department.port.infrastructure.DepartmentDataGateway;

import java.util.List;

public class DepartmentService implements DepartmentRequester {

    private final DepartmentDataGateway departmentDataGateway;

    public DepartmentService(DepartmentDataGateway departmentDataGateway) {
        this.departmentDataGateway = departmentDataGateway;
    }

    @Override
    public List<Department> getDepartments() {
        return departmentDataGateway.getDepartments();
    }

    @Override
    public Department getDepartmentById(String deptno) {
        return departmentDataGateway.getDepartmentById(deptno);
    }

    @Override
    public Department addDepartment(Department department) {
        return departmentDataGateway.addDepartment(department);
    }
}
