package fr.red.services.employe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeUI {

    private String empno;
    private String ename;
    private String job;
    private Date hiredate;
    private Double salary;
    private String deptno;

}
