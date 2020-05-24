package fr.red.services.employe;

import fr.red.services.department.model.Department;
import fr.red.services.employe.model.Employe;
import fr.red.services.employe.port.infrastructure.EmployeDataGateway;
import fr.red.services.employe.service.EmployeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class EmployeServiceTest {

    @InjectMocks
    EmployeService employeService;

    @Mock
    EmployeDataGateway employeDataGateway;

    @Test
    public void testGetEmloyes() throws ParseException {

        List<Employe> expectedEmployes = populateEmployesList();

        Mockito.when(employeDataGateway.getEmployes()).thenReturn(expectedEmployes);

        List<Employe> result = employeService.getEmployes();

        Assert.assertNotNull(result);
        Assert.assertNotEquals(0, result.size());
        Assert.assertEquals(2, result.size());

        Employe employe1 = result.get(0);
        Employe employe2 = result.get(1);

        //Employe1
        Assert.assertEquals("7369", employe1.getEmpno());
        Assert.assertEquals("SMITH", employe1.getEname());
        Assert.assertEquals("CLERK", employe1.getJob());
        Assert.assertEquals(toDate("17-12-1980"), employe1.getHiredate());
        Assert.assertEquals(Double.valueOf(800d), employe1.getSalary());
        Assert.assertEquals("20", employe1.getDepartment().getDeptno());
        Assert.assertNull(employe1.getDepartment().getDname());
        Assert.assertNull(employe1.getDepartment().getLoc());

        //Employe2
        Assert.assertEquals("7499", employe2.getEmpno());
        Assert.assertEquals("ALLEN", employe2.getEname());
        Assert.assertEquals("SALESMAN", employe2.getJob());
        Assert.assertEquals(toDate("20-2-1981"), employe2.getHiredate());
        Assert.assertEquals(Double.valueOf(1600d), employe2.getSalary());
        Assert.assertEquals("30", employe2.getDepartment().getDeptno());
        Assert.assertNull(employe2.getDepartment().getDname());
        Assert.assertNull(employe2.getDepartment().getLoc());
    }

    @Test
    public void testGetEmployeById() throws ParseException {
        Mockito.when(employeDataGateway.getEmployeById("7369")).thenReturn(getExpectedEmploye());
        Employe result = employeService.getEmployeById("7369");

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

        Employe employeToAdd = Employe.builder()
                                      .empno("7369")
                                      .ename("SMITH")
                                      .job("CLERK")
                                      .hiredate(toDate("17-12-1980"))
                                      .salary(800d)
                                      .department(Department.builder()
                                                            .deptno("20")
                                                            .build()).build();

        Employe expectedEmploye = getExpectedEmploye();

        Mockito.when(employeDataGateway.addEmploye(employeToAdd)).thenReturn(expectedEmploye);

        Employe result = employeService.addEmploye(employeToAdd);

        Assert.assertNotNull(employeToAdd);
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

        Employe employeToDelete = Employe.builder()
                               .empno("7369")
                               .ename("SMITH")
                               .job("CLERK")
                               .hiredate(toDate("17-12-1980"))
                               .salary(800d)
                               .department(Department.builder().deptno("20").build())
                               .build();

        employeService.deleteEmploye(employeToDelete);

        Mockito.verify(employeDataGateway , Mockito.times(1)).deleteEmploye(employeToDelete);
    }

    private List<Employe> populateEmployesList() throws ParseException {

        List<Employe> employes = new ArrayList<>();

        employes.add(new Employe("7369", "SMITH", "CLERK", toDate("17-12-1980"), 800d, Department.builder().deptno("20").build()));
        employes.add(new Employe("7499","ALLEN","SALESMAN",toDate("20-2-1981"),1600d,Department.builder().deptno("30").build()));

        return employes;

    }

    private Employe getExpectedEmploye() throws ParseException {
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
