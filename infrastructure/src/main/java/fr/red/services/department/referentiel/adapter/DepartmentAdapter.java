package fr.red.services.department.referentiel.adapter;

import fr.red.services.department.model.Department;
import fr.red.services.department.port.infrastructure.DepartmentDataGateway;
import fr.red.services.department.referentiel.mapper.DepartmentMapper;
import fr.red.services.department.referentiel.model.RefDepartment;
import fr.red.services.department.referentiel.repository.DepartmentRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentAdapter implements DepartmentDataGateway {

    private final DepartmentRepository deptRepository;

    public DepartmentAdapter(DepartmentRepository deptRepository) {
        this.deptRepository = deptRepository;
    }

    @Override
    public List<Department> getDepartments() {
        return DepartmentMapper.mapRefDepartmentsToDepartments(deptRepository.findAll());
    }

    @Override
    public Department getDepartmentById(String deptno) {
        return deptRepository.findById(deptno).map(refDepartment -> DepartmentMapper.mapRefDepartmentToDepartment(refDepartment)).orElse(null);
    }

    @Override
    public Department addDepartment(Department department) {

        RefDepartment refDepartment = DepartmentMapper.mapDepartmentToRefDepartment(department);

        RefDepartment addedDepartment = deptRepository.save(refDepartment);

        return DepartmentMapper.mapRefDepartmentToDepartment(addedDepartment);

    }
}
