package bd.edu.seu.wcfrontendnavigation.service;

import bd.edu.seu.wcfrontendnavigation.model.Employee;
import bd.edu.seu.wcfrontendnavigation.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${employeeUrl}/employees")
    private String employeeUrl;

    public Employee insertEmployee(Employee employee){
        HttpEntity<Employee> request = new HttpEntity<>(employee);
        ResponseEntity<Employee> response = restTemplate
                .exchange(employeeUrl, HttpMethod.POST, request, Employee.class);

        Employee responseBody = response.getBody();
        return responseBody;
    }

    public Employee getEmployee(String id){
        ResponseEntity<Employee> response = restTemplate.exchange(
                employeeUrl + "/" + id,
                HttpMethod.GET,
                null,
                Employee.class);
        Employee employee = response.getBody();
        return employee;
    }

    public List<Employee> findAll(){
        ResponseEntity<List<Employee>> response = restTemplate.exchange(
                employeeUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Employee>>(){});
        List<Employee> employeeList = response.getBody();
        return employeeList;
    }

    public Employee updateEmployee(String id, Employee employee) {
        String resourceUrl =
                employeeUrl + "/" + id;
        HttpEntity<Employee> requestUpdate = new HttpEntity<>(employee);
        restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Employee.class);
        return requestUpdate.getBody();
    }

}
