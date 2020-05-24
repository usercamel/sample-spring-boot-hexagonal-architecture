package fr.red.services.department;

import fr.red.services.department.model.Department;
import fr.red.services.department.port.infrastructure.DepartmentDataGateway;
import fr.red.services.department.service.DepartmentService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceTest {

    @InjectMocks
    DepartmentService departmentService;

    @Mock
    DepartmentDataGateway departmentDataGateway;

    @Test
    public void testGetDepartments(){
        List<Department> expectedDepartments = populateDepartmentsList();

        Mockito.when(departmentDataGateway.getDepartments()).thenReturn(expectedDepartments);
        List<Department> result = departmentService.getDepartments();

        Assert.assertNotNull(result);
        Assert.assertNotEquals(0, result.size());
        Assert.assertEquals(2, result.size());

        Department department1 = result.get(0);
        Department department2 = result.get(1);

        //Department1
        Assert.assertEquals("1", department1.getDeptno());
        Assert.assertEquals("ACCOUNTING", department1.getDname());
        Assert.assertEquals("NEW YORK", department1.getLoc());

        //Department2
        Assert.assertEquals("2", department2.getDeptno());
        Assert.assertEquals("RESEARCH", department2.getDname());
        Assert.assertEquals("DALLAS", department2.getLoc());

    }

    @Test
    public void testGetDepartmentById(){
        Mockito.when(departmentDataGateway.getDepartmentById("1")).thenReturn(expectedDepartment());
        Department result = departmentService.getDepartmentById("1");

        Assert.assertEquals("1", result.getDeptno());
        Assert.assertEquals("ACCOUNTING", result.getDname());
        Assert.assertEquals("NEW YORK", result.getLoc());


    }

    @Test
    public void testAddDepartment(){
        Department departmentToAdd = Department.builder()
                                               .deptno("1")
                                               .dname("ACCOUNTING")
                                               .loc("NEW YORK")
                                               .build();

        Mockito.when(departmentDataGateway.addDepartment(departmentToAdd)).thenReturn(expectedDepartment());
        Department result = departmentService.addDepartment(departmentToAdd);

        Assert.assertEquals("1", result.getDeptno());
        Assert.assertEquals("ACCOUNTING", result.getDname());
        Assert.assertEquals("NEW YORK", result.getLoc());

    }

    private List<Department> populateDepartmentsList(){
        return Arrays.asList(Department.builder()
                                       .deptno("1")
                                       .dname("ACCOUNTING")
                                       .loc("NEW YORK")
                                       .build(),
                             Department.builder()
                                       .deptno("2")
                                       .dname("RESEARCH")
                                       .loc("DALLAS")
                                       .build());
    }

    private Department expectedDepartment(){
        return Department.builder()
                         .deptno("1")
                         .dname("ACCOUNTING")
                         .loc("NEW YORK")
                         .build();
    }


}
