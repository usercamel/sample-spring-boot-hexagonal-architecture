package fr.red.services.employe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.red.services.department.model.Department;
import fr.red.services.employe.model.Employe;
import fr.red.services.employe.model.EmployeUI;
import fr.red.services.employe.port.application.EmployeRequester;
import fr.red.services.employe.rest.EmployeResource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeResourceTest {

    @Mock
    EmployeRequester employeRequester;

    @InjectMocks
    EmployeResource employeResource;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(employeResource).build();
    }


    @Test
    public void testGetEmployes() throws Exception {

        List<Employe> employes = populateEmployesList();

        Mockito.when(employeRequester.getEmployes()).thenReturn(employes);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employes").contentType(MediaType.APPLICATION_JSON)).andExpect(
                MockMvcResultMatchers.status().isOk())
                                     .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<EmployeUI> employesUI = Arrays.asList(mapper.readValue(content, EmployeUI[].class));
        Assert.assertNotNull(employesUI);
        Assert.assertEquals(2, employesUI.size());
        Mockito.verify(employeRequester , Mockito.times(1)).getEmployes();
    }

    @Test
    public void testGetEmployeById() throws Exception {
        Mockito.when(employeRequester.getEmployeById("7369")).thenReturn(getExpectedEmploye());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employes/{id}", "7369").contentType(MediaType.APPLICATION_JSON)).andExpect(
                MockMvcResultMatchers.status().isOk())
                                     .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        EmployeUI result = mapper.readValue(content, EmployeUI.class);
        Assert.assertNotNull(result);
        Assert.assertEquals("7369", result.getEmpno());
        Assert.assertEquals("SMITH", result.getEname());
        Assert.assertEquals("CLERK", result.getJob());
        Assert.assertEquals(toDate("17-12-1980"), result.getHiredate());
        Assert.assertEquals(Double.valueOf(800d), result.getSalary());
        Assert.assertEquals("20", result.getDeptno());



    }

    @Test
    public void testAddEmploye() throws Exception {

        Mockito.when(employeRequester.addEmploye(ArgumentMatchers.any())).thenReturn(getExpectedEmploye());
        EmployeUI employeUIToAdd = EmployeUI.builder()
                                          .empno("7369")
                                          .ename("SMITH")
                                          .job("CLERK")
                                          .hiredate(toDate("17-12-1980"))
                                          .salary(800d)
                                          .deptno("20").build();

        String inputJson = mapToJson(employeUIToAdd);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employes").contentType(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(201, status);
        ObjectMapper mapper = new ObjectMapper();
        EmployeUI result = mapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                                               new TypeReference<>() {
                                               });
        Assert.assertNotNull(result);

    }

    @Test
    public void testDeleteEmploye() throws Exception {
        Mockito.when(employeRequester.getEmployeById("7369")).thenReturn(getExpectedEmploye());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/employes/{id}", "7369").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Map result = mapper.readValue(content, Map.class);
        Assert.assertNotNull(result);
        Assert.assertEquals("success", result.get("Deleting employe"));

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


    private List<Employe> populateEmployesList() throws ParseException {

        List<Employe> employes = new ArrayList<>();

        employes.add(new Employe("7369", "SMITH", "CLERK", toDate("17-12-1980"), 800d, Department.builder().deptno("20").build()));
        employes.add(new Employe("7499","ALLEN","SALESMAN",toDate("20-2-1981"),1600d,Department.builder().deptno("30").build()));

        return employes;

    }

    private String mapToJson(EmployeUI obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private Date toDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return formatter.parse(dateString);
    }
}
