package fr.red.services.configuration;

import fr.red.services.department.referentiel.model.RefDepartment;
import fr.red.services.department.referentiel.repository.DepartmentRepository;
import fr.red.services.employe.referentiel.model.RefEmploye;
import fr.red.services.employe.referentiel.repository.EmployeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Configuration
public class InfrastructureBeanConfiguration {

    @Bean
    DepartmentRepository departmentRepository(){
        DepartmentRepository departmentRepository = new DepartmentRepository();
        departmentRepository.save(new RefDepartment("10", "ACCOUNTING", "NEW YORK"));
        departmentRepository.save(new RefDepartment("20", "RESEARCH", "DALLAS"));
        departmentRepository.save(new RefDepartment("30", "SALES", "CHICAGO"));
        departmentRepository.save(new RefDepartment("40", "OPERATIONS", "BOSTON"));
        return departmentRepository;
    }

    @Bean
    EmployeRepository employeRepository() throws ParseException {
        EmployeRepository employeRepository = new EmployeRepository();
        employeRepository.save(new RefEmploye("7369","SMITH","CLERK",toDate("17-12-1980"),800d,"20"));
        employeRepository.save(new RefEmploye("7499","ALLEN","SALESMAN",toDate("20-2-1981"),1600d,"30"));
        employeRepository.save(new RefEmploye("7521","WARD","SALESMAN",toDate("22-2-1981"),1250d,"30"));
        employeRepository.save(new RefEmploye("7566","JONES","MANAGER",toDate("02-4-1981"),2975d,"20"));
        employeRepository.save(new RefEmploye("7654","MARTIN","SALESMAN",toDate("28-9-1981"),1250d,"30"));
        employeRepository.save(new RefEmploye("7698","BLAKE","MANAGER",toDate("01-5-1981"),2850d,"30"));
        employeRepository.save(new RefEmploye("7782","CLARK","MANAGER",toDate("09-6-1981"),2450d,"10"));
        employeRepository.save(new RefEmploye("7788","SCOTT","ANALYST",toDate("13-7-87"),3000d,"20"));
        employeRepository.save(new RefEmploye("7839","KING","PRESIDENT",toDate("17-11-1981"),5000d,"10"));
        employeRepository.save(new RefEmploye("7844","TURNER","SALESMAN",toDate("8-9-1981"),1500d,"30"));

        return employeRepository;
    }

    private Date toDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return formatter.parse(dateString);
    }
}
