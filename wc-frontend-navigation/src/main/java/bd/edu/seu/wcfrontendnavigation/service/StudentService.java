package bd.edu.seu.wcfrontendnavigation.service;

import bd.edu.seu.wcfrontendnavigation.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class StudentService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${studentUrl}/students")
    private String studentUrl;

    public Student insertStudent(Student student){
        HttpEntity<Student> request = new HttpEntity<>(student);
        ResponseEntity<Student> response = restTemplate
                .exchange(studentUrl, HttpMethod.POST, request, Student.class);

        return response.getBody();
    }

    public Student getStudent(String id){
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
        String resourceUrl =
                studentUrl + "/" + id.toString();
        HttpEntity<Student> requestUpdate = new HttpEntity<>(student);
        restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Student.class);
        return requestUpdate.getBody();
    }

}
