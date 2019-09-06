package bd.edu.seu.student.controller;

import bd.edu.seu.student.exception.ResourceAlreadyExistsException;
import bd.edu.seu.student.exception.ResourceDoesNotExistsException;
import bd.edu.seu.student.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import bd.edu.seu.student.service.StudentService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/students")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("")
    public ResponseEntity<List<Student>> findAll() {
        List<Student> studentList = studentService.findAll();
        return ResponseEntity.ok(studentList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> findById(@PathVariable Long id) {
        try {
            Student student = studentService.findById(id);
            return ResponseEntity.ok(student);
        } catch (ResourceDoesNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Student> insertStudent(@RequestBody Student student) {
        try {
            Student insertedStudent = studentService.insertStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertedStudent);
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable Long id) {
        try {
            Boolean deleteByid = studentService.deleteByid(id);
            return ResponseEntity.ok(id);
        } catch (ResourceDoesNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            Student updatedStudent = studentService.updateStudent(id, student);
            return ResponseEntity.ok(updatedStudent);
        } catch (ResourceDoesNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
