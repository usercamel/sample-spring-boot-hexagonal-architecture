package fr.red.services.employe.service;

import fr.red.services.employe.model.Employe;
import fr.red.services.employe.port.application.EmployeRequester;
import fr.red.services.employe.port.infrastructure.EmployeDataGateway;

import java.util.List;

public class EmployeService implements EmployeRequester {

    private final EmployeDataGateway employeDataGateway;

    public EmployeService(EmployeDataGateway employeDataGateway) {
        this.employeDataGateway = employeDataGateway;
    }

    @Override
    public List<Employe> getEmployes() {
        return employeDataGateway.getEmployes();
    }

    @Override
    public Employe getEmployeById(String empno) {
        return employeDataGateway.getEmployeById(empno);
    }

    @Override
    public Employe addEmploye(Employe employe) {
        return employeDataGateway.addEmploye(employe);
    }

    @Override
    public void deleteEmploye(Employe employe) {
        employeDataGateway.deleteEmploye(employe);
    }
}
