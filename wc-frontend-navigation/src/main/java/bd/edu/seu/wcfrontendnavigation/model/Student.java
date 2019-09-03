package bd.edu.seu.wcfrontendnavigation.model;

import bd.edu.seu.wcfrontendnavigation.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Student {
    private Long id;
    private String name;
    private Double cgpa;
    private String loginPass;
    private String email;
    private Double crCompleted;
    private String program;
    private Status paymentStatus;
    private String trxID;
    private Double feePaid;
    private List<Course> coursList;

    public Student(Long id, String name, Double cgpa, String loginPass, @Email String email, String program) {
        this.id = id;
        this.name = name;
        this.cgpa = cgpa;
        this.loginPass = loginPass;
        this.email = email;
        this.program = program;
    }

    public void registerCourse(Course ... course){
        if(coursList == null)
            coursList = new ArrayList<>();

        Arrays.stream(course).forEach(tempCourse -> coursList.add(tempCourse));
    }
}
