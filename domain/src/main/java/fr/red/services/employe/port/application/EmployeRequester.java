package fr.red.services.employe.port.application;

import fr.red.services.employe.model.Employe;

import java.util.List;

public interface EmployeRequester {

    List<Employe> getEmployes();

    Employe getEmployeById(String empno);

    Employe addEmploye(Employe employe);

    void deleteEmploye(Employe employe);
}
