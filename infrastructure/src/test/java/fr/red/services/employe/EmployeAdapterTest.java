package fr.red.services.employe;

import fr.red.services.department.model.Department;
import fr.red.services.employe.model.Employe;
import fr.red.services.employe.referentiel.adapter.EmployeAdapter;
import fr.red.services.employe.referentiel.model.RefEmploye;
import fr.red.services.employe.referentiel.repository.EmployeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeAdapterTest {

    @InjectMocks
    EmployeAdapter employeAdapter;

    @Mock
    EmployeRepository employeRepository;

    @Test
    public void testGetEmloyes() throws ParseException {

        List<RefEmploye> expectedEmployes = populateEmployesList();

        Mockito.when(employeRepository.findAll()).thenReturn(expectedEmployes);

        List<Employe> result = employeAdapter.getEmployes();

        Assert.assertNotNull(result);
        Assert.assertNotEquals(0, result.size());
        Assert.assertEquals(2, result.size());

        Employe refEmploye1 = result.get(0);
        Employe refEmploye2 = result.get(1);

        //Employe1
        Assert.assertEquals("7369", refEmploye1.getEmpno());
        Assert.assertEquals("SMITH", refEmploye1.getEname());
        Assert.assertEquals("CLERK", refEmploye1.getJob());
        Assert.assertEquals(toDate("17-12-1980"), refEmploye1.getHiredate());
        Assert.assertEquals(Double.valueOf(800d), refEmploye1.getSalary());
        Assert.assertEquals("20", refEmploye1.getDepartment().getDeptno());
        Assert.assertNull(refEmploye1.getDepartment().getDname());
        Assert.assertNull(refEmploye1.getDepartment().getLoc());

        //Employe2
        Assert.assertEquals("7499", refEmploye2.getEmpno());
        Assert.assertEquals("ALLEN", refEmploye2.getEname());
        Assert.assertEquals("SALESMAN", refEmploye2.getJob());
        Assert.assertEquals(toDate("20-2-1981"), refEmploye2.getHiredate());
        Assert.assertEquals(Double.valueOf(1600d), refEmploye2.getSalary());
        Assert.assertEquals("30", refEmploye2.getDepartment().getDeptno());
        Assert.assertNull(refEmploye2.getDepartment().getDname());
        Assert.assertNull(refEmploye2.getDepartment().getLoc());
    }

    @Test
    public void testGetEmployeById() throws ParseException {
        Mockito.when(employeRepository.findById("7369")).thenReturn(Optional.ofNullable(getExpectedRefEmploye()));
        Employe result = employeAdapter.getEmployeById("7369");

        Assert.assertNotNull(result);
        Assert.assertEquals("7369", result.getEmpno());
        Assert.assertEquals("SMITH", result.getEname());
        Assert.assertEquals("CLERK", result.getJob());
        Assert.assertEquals(toDate("17-12-1980"), result.getHiredate());
        Assert.assertEquals(Double.valueOf(800d), result.getSalary());
        Assert.assertEquals("20", result.getDepartment().getDeptno());
        Assert.assertNull(result.getDepartment().getDname());
        Assert.assertNull(result.getDepartment().getLoc());

    }

    @Test
    public void testAddEmploye() throws ParseException {

        RefEmploye refEmployeToAdd = RefEmploye.builder()
                                      .empno("7369")
                                      .ename("SMITH")
                                      .job("CLERK")
                                      .hiredate(toDate("17-12-1980"))
                                      .salary(800d)
                                      .deptno("20").build();

        RefEmploye expectedRefEmploye = getExpectedRefEmploye();

        Mockito.when(employeRepository.save(refEmployeToAdd)).thenReturn(expectedRefEmploye);

        Employe result = employeAdapter.addEmploye(populateEmploye());

        Assert.assertNotNull(refEmployeToAdd);
        Assert.assertEquals("7369", result.getEmpno());
        Assert.assertEquals("SMITH", result.getEname());
        Assert.assertEquals("CLERK", result.getJob());
        Assert.assertEquals(toDate("17-12-1980"), result.getHiredate());
        Assert.assertEquals(Double.valueOf(800d), result.getSalary());
        Assert.assertEquals("20", result.getDepartment().getDeptno());
        Assert.assertNull(result.getDepartment().getDname());
        Assert.assertNull(result.getDepartment().getLoc());
    }

    @Test
    public void testDeleteEmploye() throws ParseException {

        employeAdapter.deleteEmploye(populateEmploye());

        Mockito.verify(employeRepository , Mockito.times(1)).delete(getExpectedRefEmploye());
    }

    private List<RefEmploye> populateEmployesList() throws ParseException {

        List<RefEmploye> employes = new ArrayList<>();

        employes.add(new RefEmploye("7369", "SMITH", "CLERK", toDate("17-12-1980"), 800d, "20"));
        employes.add(new RefEmploye("7499","ALLEN","SALESMAN",toDate("20-2-1981"),1600d,"30"));

        return employes;

    }

    private RefEmploye getExpectedRefEmploye() throws ParseException {
        return RefEmploye.builder()
                      .empno("7369")
                      .ename("SMITH")
                      .job("CLERK")
                      .hiredate(toDate("17-12-1980"))
                      .salary(800d)
                      .deptno("20").build();
    }

    private Employe populateEmploye() throws ParseException {
        return Employe.builder()
                      .empno("7369")
                      .ename("SMITH")
                      .job("CLERK")
                      .hiredate(toDate("17-12-1980"))
                      .salary(800d)
                      .department(Department.builder()
                                            .deptno("20")
                                            .build()).build();
    }


    private Date toDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return formatter.parse(dateString);
    }
}
