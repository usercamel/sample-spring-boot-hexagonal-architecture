package fr.red.services.department;

import fr.red.services.department.model.Department;
import fr.red.services.department.referentiel.adapter.DepartmentAdapter;
import fr.red.services.department.referentiel.model.RefDepartment;
import fr.red.services.department.referentiel.repository.DepartmentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentAdapterTest {

    @InjectMocks
    DepartmentAdapter departmentAdapter;

    @Mock
    DepartmentRepository departmentRepository;

    @Test
    public void testGetDepartments(){
        List<RefDepartment> expectedDepartments = populateRefDepartmentsList();

        Mockito.when(departmentRepository.findAll()).thenReturn(expectedDepartments);
        List<Department> result = departmentAdapter.getDepartments();

        Assert.assertNotNull(result);
        Assert.assertNotEquals(0, result.size());
        Assert.assertEquals(2, result.size());

        Department refDepartment1 = result.get(0);
        Department refDepartment2 = result.get(1);

        //Department1
        Assert.assertEquals("1", refDepartment1.getDeptno());
        Assert.assertEquals("ACCOUNTING", refDepartment1.getDname());
        Assert.assertEquals("NEW YORK", refDepartment1.getLoc());

        //Department2
        Assert.assertEquals("2", refDepartment2.getDeptno());
        Assert.assertEquals("RESEARCH", refDepartment2.getDname());
        Assert.assertEquals("DALLAS", refDepartment2.getLoc());

    }

    @Test
    public void testGetDepartmentById(){
        Mockito.when(departmentRepository.findById("1")).thenReturn(Optional.ofNullable(expectedRefDepartment()));
        Department result = departmentAdapter.getDepartmentById("1");

        Assert.assertEquals("1", result.getDeptno());
        Assert.assertEquals("ACCOUNTING", result.getDname());
        Assert.assertEquals("NEW YORK", result.getLoc());


    }

    @Test
    public void testAddDepartment(){
        RefDepartment refDepartmentToAdd = RefDepartment.builder()
                                               .deptno("1")
                                               .dname("ACCOUNTING")
                                               .loc("NEW YORK")
                                               .build();
        Mockito.when(departmentRepository.save(refDepartmentToAdd)).thenReturn(expectedRefDepartment());

        Department result = departmentAdapter.addDepartment(departmentToAdd());

        Assert.assertEquals("1", result.getDeptno());
        Assert.assertEquals("ACCOUNTING", result.getDname());
        Assert.assertEquals("NEW YORK", result.getLoc());

    }

    private List<RefDepartment> populateRefDepartmentsList(){
        return Arrays.asList(RefDepartment.builder()
                                       .deptno("1")
                                       .dname("ACCOUNTING")
                                       .loc("NEW YORK")
                                       .build(),
                             RefDepartment.builder()
                                       .deptno("2")
                                       .dname("RESEARCH")
                                       .loc("DALLAS")
                                       .build());
    }

    private RefDepartment expectedRefDepartment(){
        return RefDepartment.builder()
                         .deptno("1")
                         .dname("ACCOUNTING")
                         .loc("NEW YORK")
                         .build();
    }

    private Department departmentToAdd() {
        return Department.builder().deptno("1").dname("ACCOUNTING").loc("NEW YORK").build();
    }
}
