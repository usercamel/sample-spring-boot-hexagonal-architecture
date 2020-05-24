package fr.red.services.employe.port.infrastructure;

import fr.red.services.employe.model.Employe;

import java.util.List;

public interface EmployeDataGateway {

    List<Employe> getEmployes();

    Employe getEmployeById(String empno);

    Employe addEmploye(Employe employe);

    void deleteEmploye(Employe employe);
}
