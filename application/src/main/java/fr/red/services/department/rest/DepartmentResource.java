package fr.red.services.department.rest;

import fr.red.services.common.exception.BadRequestException;
import fr.red.services.common.exception.ConflictException;
import fr.red.services.common.exception.NotFoundException;
import fr.red.services.department.mapper.DepartmentMapper;
import fr.red.services.department.model.Department;
import fr.red.services.department.model.DepartmentUI;
import fr.red.services.department.port.application.DepartmentRequester;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/departments")
public class DepartmentResource {


    private final DepartmentRequester departmentRequester;

    public DepartmentResource(DepartmentRequester departmentRequester) {
        this.departmentRequester = departmentRequester;
    }

    @GetMapping()
    public ResponseEntity<List<DepartmentUI>> getDepartments(){
        List<Department> departments = departmentRequester.getDepartments();

        List<DepartmentUI> result = DepartmentMapper.mapDepartmentsToDepartmentsUI(departments);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DepartmentUI> getDepartmentById(@PathVariable String id) {

        Department department = departmentRequester.getDepartmentById(id);

        if(department == null)
            throw new NotFoundException("Department with id:" + id + " is not found !");

        DepartmentUI result = DepartmentMapper.mapDepartmentToDepartmentUI(department);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping()
    public ResponseEntity<DepartmentUI> addDepartment(@RequestBody DepartmentUI departmentUI){

        if(StringUtils.isEmpty(departmentUI.getDeptno()))
            throw new BadRequestException("Deptno is required !");

        Department department = DepartmentMapper.mapDepartmentUIToDepartment(departmentUI);

        Department retrievedDepartment = departmentRequester.getDepartmentById(department.getDeptno());
        if(retrievedDepartment != null)
            throw new ConflictException("Department with id:" + retrievedDepartment.getDeptno() + " already exits !");


        Department dept = departmentRequester.addDepartment(department);

        DepartmentUI result = DepartmentMapper.mapDepartmentToDepartmentUI(dept);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
