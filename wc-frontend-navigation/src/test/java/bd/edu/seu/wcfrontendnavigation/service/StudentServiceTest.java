package bd.edu.seu.wcfrontendnavigation.service;

import bd.edu.seu.wcfrontendnavigation.enums.Status;
import bd.edu.seu.wcfrontendnavigation.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;
    @Value("${studentUrl}/students")
    private String studentUrl;

    @Test
    public void insertStudentTest() {
        Student student = new Student();
        student.setId(1236l);
        student.setName("Test guy");
        student.setEmail("test@test.com");
        student.setProgram("BSc in CSE");
        student.setLoginPass("1236");
        student.setDob(LocalDate.now());
        student.setAdmissionDate(LocalDate.now());

        Student insertStudent = studentService.insertStudent(student);
        System.out.println(insertStudent.toString());
    }

    @Test
    public void updateStudentTest(){
        Student student = new Student();
        student.setId(1236l);
        student.setName("Test guy");
        student.setEmail("test@test.com");
        student.setPaymentStatus(Status.PENDING);
        student.setFeePaid(6500.0);
        student.setTrxID("kxkj");
        student.setCgpa(3.25);
        student.setProgram("BSc in CSE");
        student.setLoginPass("1236");

        //Student result = studentService.insertStudent(student);
        System.out.println(student.toString());

        student.setName("Again Updated guy v1");
//        RestTemplate restTemplate = new RestTemplate();
//        String resourceUrl =
//                studentUrl + "/" + student.getId();
//        HttpEntity<Student> requestUpdate = new HttpEntity<>(student);
//        restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Student.class);
        Student updateStudent = studentService.updateStudent(student.getId(), student);
        System.out.println(updateStudent.toString());

    }
}
