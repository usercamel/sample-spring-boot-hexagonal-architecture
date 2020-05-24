package fr.red.services.employe.referentiel.repository;

import fr.red.services.employe.referentiel.model.RefEmploye;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public  class EmployeRepository {

    private List<RefEmploye> employes = new ArrayList<>();

    public RefEmploye save(RefEmploye refEmploye) {
        employes.add(refEmploye);
        return refEmploye;
    }

    public Optional<RefEmploye> findById(String empno) {
        return employes.stream().filter(refEmploye -> refEmploye.getEmpno().equals(empno)).findFirst();
    }

    public List<RefEmploye> findAll() {
        return employes;
    }

    public void delete(RefEmploye refEmploye){
        employes.remove(refEmploye);
    }
}
