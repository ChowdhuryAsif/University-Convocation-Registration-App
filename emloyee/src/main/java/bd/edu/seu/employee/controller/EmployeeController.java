package bd.edu.seu.employee.controller;

import bd.edu.seu.employee.service.EmployeeService;
import bd.edu.seu.employee.exception.ResourceAlreadyExistsException;
import bd.edu.seu.employee.exception.ResourceDoesNotExistsException;
import bd.edu.seu.employee.model.Employee;
import bd.edu.seu.employee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public ResponseEntity<List<Employee>> getEmployeees() {
        List<Employee> employeeList = employeeService.findAll();
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable String id) {
        try {
            Employee employee = employeeService.findById(id);
            return ResponseEntity.ok(employee);
        } catch (ResourceDoesNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Employee> insertEmployee(@RequestBody Employee employee) {
        try {
            Employee insertedEmployee = employeeService.insertEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertedEmployee);
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (ResourceDoesNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
