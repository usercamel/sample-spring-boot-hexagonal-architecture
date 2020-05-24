package fr.red.services.department.mapper;

import fr.red.services.department.model.Department;
import fr.red.services.department.model.DepartmentUI;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentMapper {


    public static Department mapDepartmentUIToDepartment(DepartmentUI departmentUI){
        return Department.builder()
                          .deptno(departmentUI.getDeptno())
                          .dname(departmentUI.getDname())
                          .loc(departmentUI.getLoc())
                          .build();
    }

    public static DepartmentUI mapDepartmentToDepartmentUI(Department department){
        return DepartmentUI.builder()
                            .deptno(department.getDeptno())
                            .dname(department.getDname())
                            .loc(department.getLoc())
                            .build();
    }

    public static List<DepartmentUI> mapDepartmentsToDepartmentsUI(List<Department> departments){
        return departments.stream().map(department -> mapDepartmentToDepartmentUI(department)).collect(Collectors.toList());
    }

}
