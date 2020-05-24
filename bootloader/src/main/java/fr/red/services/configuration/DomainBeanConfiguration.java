package fr.red.services.configuration;

import fr.red.services.department.port.infrastructure.DepartmentDataGateway;
import fr.red.services.department.service.DepartmentService;
import fr.red.services.employe.port.infrastructure.EmployeDataGateway;
import fr.red.services.employe.service.EmployeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainBeanConfiguration {

    @Bean
    DepartmentService departmentService(DepartmentDataGateway departmentDataGateway){
        return new DepartmentService(departmentDataGateway);
    }

    @Bean
    EmployeService employeService(EmployeDataGateway employeDataGateway){
        return new EmployeService(employeDataGateway);
    }

}
