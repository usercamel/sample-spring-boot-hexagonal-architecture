package fr.red.services.department.referentiel.repository;

import fr.red.services.department.referentiel.model.RefDepartment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentRepository {

    private List<RefDepartment> departments = new ArrayList<>();

    public RefDepartment save(RefDepartment department) {
        departments.add(department);
        return department;
    }

    public Optional<RefDepartment> findById(String deptno) {
        return departments.stream().filter(refDepartment -> refDepartment.getDeptno().equals(deptno)).findFirst();
    }

    public List<RefDepartment> findAll() {
        return departments;
    }

}
