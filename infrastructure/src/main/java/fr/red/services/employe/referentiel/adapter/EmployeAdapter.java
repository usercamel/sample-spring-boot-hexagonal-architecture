package fr.red.services.employe.referentiel.adapter;

import fr.red.services.employe.model.Employe;
import fr.red.services.employe.port.infrastructure.EmployeDataGateway;
import fr.red.services.employe.referentiel.mapper.EmployeMapper;
import fr.red.services.employe.referentiel.model.RefEmploye;
import fr.red.services.employe.referentiel.repository.EmployeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeAdapter implements EmployeDataGateway {

    private final EmployeRepository employeRepository;

    public EmployeAdapter(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    @Override
    public List<Employe> getEmployes() {
        return EmployeMapper.mapRefEmployesToEmployes(employeRepository.findAll());
    }

    @Override
    public Employe getEmployeById(String empno) {
        return employeRepository.findById(empno).map(refEmploye -> EmployeMapper.mapRefEmployeToEmploye(refEmploye)).orElse(null);
    }

    @Override
    public Employe addEmploye(Employe employe) {

        RefEmploye refEmploye = EmployeMapper.mapEmployeToRefEmploye(employe);

        return EmployeMapper.mapRefEmployeToEmploye(employeRepository.save(refEmploye));
    }

    @Override
    public void deleteEmploye(Employe employe) {
        employeRepository.delete(EmployeMapper.mapEmployeToRefEmploye(employe));
    }
}
