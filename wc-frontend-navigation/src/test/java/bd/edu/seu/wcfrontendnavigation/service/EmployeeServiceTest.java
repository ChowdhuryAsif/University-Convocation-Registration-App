package bd.edu.seu.wcfrontendnavigation.service;

import bd.edu.seu.wcfrontendnavigation.enums.Role;
import bd.edu.seu.wcfrontendnavigation.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {
    @Autowired
    private EmployeeService employeeService;

    @Test
    public void findAllTest(){
        List<Employee> employeeList = employeeService.findAll();

        employeeList.forEach(System.out::println);

    }

    @Test
    public void insertTest(){
        Employee employee = new Employee("RB", "Rajon Bardhan", "RB", "BSc in CSE", Role.COORDINATOR);

        Employee result = employeeService.insertEmployee(employee);

        System.out.println(result.toString());
    }

    @Test
    public void updateTest(){
        Employee employee = new Employee("RB", "Rajon Bardhan", "RB", "BSc in CSE", Role.ADMISSION_OFFICER);

        Employee updateEmployee = employeeService.updateEmployee("RB", employee);

        System.out.println(updateEmployee);
    }
}
