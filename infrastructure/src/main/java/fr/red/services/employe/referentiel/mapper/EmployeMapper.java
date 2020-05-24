package fr.red.services.employe.referentiel.mapper;

import fr.red.services.department.model.Department;
import fr.red.services.employe.model.Employe;
import fr.red.services.employe.referentiel.model.RefEmploye;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeMapper {

    public static Employe mapRefEmployeToEmploye(RefEmploye refEmploye){
        return Employe.builder()
                      .empno(refEmploye.getEmpno())
                      .ename(refEmploye.getEname())
                      .job(refEmploye.getJob())
                      .hiredate(refEmploye.getHiredate())
                      .salary(refEmploye.getSalary())
                      .department(Department.builder().deptno(refEmploye.getDeptno()).build())
                      .build();
    }

    public static RefEmploye mapEmployeToRefEmploye(Employe employe){
        return RefEmploye.builder()
                         .empno(employe.getEmpno())
                         .ename(employe.getEname())
                         .job(employe.getJob())
                         .hiredate(employe.getHiredate())
                         .salary(employe.getSalary())
                         .deptno(employe.getDepartment().getDeptno())
                         .build();
    }

    public static List<Employe> mapRefEmployesToEmployes(List<RefEmploye> refEmployes){
        return refEmployes.stream().map(refEmploye -> mapRefEmployeToEmploye(refEmploye)).collect(Collectors.toList());
    }
}