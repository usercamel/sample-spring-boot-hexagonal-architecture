package fr.red.services.employe.mapper;

import fr.red.services.department.model.Department;
import fr.red.services.employe.model.Employe;
import fr.red.services.employe.model.EmployeUI;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeMapper {


    public static Employe mapEmployeUIToEmploye(EmployeUI employeUI){
        return Employe.builder()
                      .empno(employeUI.getEmpno())
                      .ename(employeUI.getEname())
                      .job(employeUI.getJob())
                      .hiredate(employeUI.getHiredate())
                      .salary(employeUI.getSalary())
                      .department(Department.builder().deptno(employeUI.getDeptno()).build())
                      .build();
    }

    public static EmployeUI mapEmployeToEmployeUI(Employe employe){
        return EmployeUI.builder()
                        .empno(employe.getEmpno())
                        .ename(employe.getEname())
                        .job(employe.getJob())
                        .hiredate(employe.getHiredate())
                        .salary(employe.getSalary())
                        .deptno(employe.getDepartment().getDeptno())
                        .build();
    }

    public static List<EmployeUI> mapEmployesToEmployesUI(List<Employe> employes){
        return employes.stream().map(employe -> mapEmployeToEmployeUI(employe)).collect(Collectors.toList());
    }
}
