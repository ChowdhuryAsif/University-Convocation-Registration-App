package bd.edu.seu.employee.service;

import bd.edu.seu.employee.enums.Role;
import bd.edu.seu.employee.model.LoginToken;
import bd.edu.seu.employee.repository.EmployeeRepository;
import bd.edu.seu.employee.exception.ResourceAlreadyExistsException;
import bd.edu.seu.employee.exception.ResourceDoesNotExistsException;
import bd.edu.seu.employee.model.Employee;
import bd.edu.seu.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private RestTemplate restTemplate;
    private EmployeeRepository employeeRepository;
    @Value("${authUrl}/authorization")
    private  String authUrl;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee findById(String id) throws ResourceDoesNotExistsException {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        } else
            throw new ResourceDoesNotExistsException(id + "");
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }


    public Employee insertEmployee(Employee employee) throws ResourceAlreadyExistsException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getInitial());
        if (optionalEmployee.isPresent()) {
            throw new ResourceAlreadyExistsException(employee.getInitial());
        } else {
            Employee savedEmployee = employeeRepository.save(employee);

            String authoBaseUrl = authUrl;
            LoginToken loginToken = new LoginToken(savedEmployee.getInitial(), savedEmployee.getLoginPass(), savedEmployee.getRole());
            HttpEntity<LoginToken> request = new HttpEntity<>(loginToken);
            ResponseEntity<LoginToken> response = restTemplate
                    .exchange(authoBaseUrl, HttpMethod.POST, request, LoginToken.class);

            return savedEmployee;
        }
    }

    public Employee updateEmployee(String id, Employee employee) throws ResourceDoesNotExistsException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            employee.setInitial(id);
            Employee savedEmployee = employeeRepository.save(employee);

            //updated authorization task here==
            String authoBaseUrl = authUrl;
            String username = savedEmployee.getInitial();
            String password = savedEmployee.getLoginPass();
            LoginToken loginToken = new LoginToken(username, password, savedEmployee.getRole());
            HttpEntity<LoginToken> request = new HttpEntity<>(loginToken);
            ResponseEntity<LoginToken> response = restTemplate
                    .exchange(authoBaseUrl + "/" + username, HttpMethod.PUT, request, LoginToken.class);

            return savedEmployee;
        } else {
            throw new ResourceDoesNotExistsException(id + "");
        }
    }
}
