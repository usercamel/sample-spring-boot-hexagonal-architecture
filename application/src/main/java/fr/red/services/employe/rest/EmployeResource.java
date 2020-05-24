package fr.red.services.employe.rest;

import fr.red.services.common.exception.BadRequestException;
import fr.red.services.common.exception.ConflictException;
import fr.red.services.common.exception.NotFoundException;
import fr.red.services.employe.mapper.EmployeMapper;
import fr.red.services.employe.model.Employe;
import fr.red.services.employe.model.EmployeUI;
import fr.red.services.employe.port.application.EmployeRequester;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/employes")
public class EmployeResource {

    private final EmployeRequester employeRequester;

    public EmployeResource(EmployeRequester employeRequester) {
        this.employeRequester = employeRequester;
    }

    @GetMapping()
    public ResponseEntity<List<EmployeUI>> getEmployes(){
        List<Employe> employes = employeRequester.getEmployes();

        List<EmployeUI> result = EmployeMapper.mapEmployesToEmployesUI(employes);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EmployeUI> getEmployes(@PathVariable String id) {

        Employe employe = getAndCheckEmploye(id);

        EmployeUI result = EmployeMapper.mapEmployeToEmployeUI(employe);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping()
    public ResponseEntity<EmployeUI> addEmploye(@RequestBody EmployeUI employeUI){

        if(StringUtils.isEmpty(employeUI.getEmpno()))
            throw new BadRequestException("Empno is required !");

        Employe employe = EmployeMapper.mapEmployeUIToEmploye(employeUI);

        Employe retrievedEmploye = employeRequester.getEmployeById(employe.getEmpno());
        if(retrievedEmploye != null)
            throw new ConflictException("Employe with id:" + retrievedEmploye.getEmpno() + " already exits !");

        Employe emp = employeRequester.addEmploye(employe);

        EmployeUI result = EmployeMapper.mapEmployeToEmployeUI(emp);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Map<String, String>> deleteEmploye(@PathVariable String id)  {

        Employe employe = getAndCheckEmploye(id);

        employeRequester.deleteEmploye(employe);

        Map<String, String> result = new HashMap<>();
        result.put("Deleting employe", "success");

        return ResponseEntity.ok().body(result);
    }

    private Employe getAndCheckEmploye(@PathVariable String id) {
        Employe employe = employeRequester.getEmployeById(id);

        if (employe == null)
            throw new NotFoundException("Employe with id:" + id + " is not found !");
        return employe;
    }
}
