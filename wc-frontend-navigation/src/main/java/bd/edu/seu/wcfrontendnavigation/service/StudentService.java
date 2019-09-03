package bd.edu.seu.wcfrontendnavigation.service;

import bd.edu.seu.wcfrontendnavigation.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${studentUrl}/students")
    private String studentUrl;

    public Student getStudent(String id){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Student> response = restTemplate.exchange(
                studentUrl + "/" + id,
                HttpMethod.GET,
                null,
                Student.class);
        Student student = response.getBody();
        return student;
    }

    //TODO Check this============
    public Student updateStudent(Long id, Student student) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl =
                studentUrl + '/' + id;
        HttpEntity<Student> requestUpdate = new HttpEntity<>(student);
        restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Student.class);
        return student;
    }

}
