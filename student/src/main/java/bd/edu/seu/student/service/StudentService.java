package bd.edu.seu.student.service;

import bd.edu.seu.student.enums.Role;
import bd.edu.seu.student.exception.ResourceAlreadyExistsException;
import bd.edu.seu.student.exception.ResourceDoesNotExistsException;
import bd.edu.seu.student.model.LoginToken;
import bd.edu.seu.student.model.Student;
import bd.edu.seu.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private RestTemplate restTemplate;
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student findById(Long id) throws ResourceDoesNotExistsException {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get();
        } else
            throw new ResourceDoesNotExistsException(id + "");
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Boolean deleteByid(Long id) throws ResourceDoesNotExistsException {
        Optional<Student> student = studentRepository.findById(id);
        student.ifPresent(student1 -> studentRepository.deleteById(id));
        student.orElseThrow(() -> new ResourceDoesNotExistsException(id + ""));
        return true;
    }

    public Student insertStudent(Student student) throws ResourceAlreadyExistsException {
        Optional<Student> optionalStudent = studentRepository.findById(student.getId());
        if (optionalStudent.isPresent()) {
            throw new ResourceAlreadyExistsException(student.getId() + "");
        } else {
            Student savedStudent = studentRepository.save(student);

            //updated authorization task here==
            String authoBaseUrl = "https://wc-uni-authorization.herokuapp.com/api/v1/authorization";
            LoginToken loginToken = new LoginToken(savedStudent.getId().toString(), savedStudent.getId().toString(), Role.STUDENT);
            HttpEntity<LoginToken> request = new HttpEntity<>(loginToken);
            ResponseEntity<LoginToken> response = restTemplate
                    .exchange(authoBaseUrl, HttpMethod.POST, request, LoginToken.class);

            return savedStudent;
        }
    }

    public Student updateStudent(Long id, Student student) throws ResourceDoesNotExistsException {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            student.setId(id);
            Student savedStudent = studentRepository.save(student);

            //updated authorization task here==
            String authoBaseUrl = "https://wc-uni-authorization.herokuapp.com/api/v1/authorization";
            String username = savedStudent.getId().toString();
            String password = savedStudent.getLoginPass();
            LoginToken loginToken = new LoginToken(username, password, Role.STUDENT);
            HttpEntity<LoginToken> request = new HttpEntity<>(loginToken);
            ResponseEntity<LoginToken> response = restTemplate
                    .exchange(authoBaseUrl + "/" + username, HttpMethod.PUT, request, LoginToken.class);

            return savedStudent;
        } else {
            throw new ResourceDoesNotExistsException(id + "");
        }
    }
}
