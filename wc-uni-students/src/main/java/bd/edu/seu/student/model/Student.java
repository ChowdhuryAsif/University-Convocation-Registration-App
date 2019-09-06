package bd.edu.seu.student.model;

import bd.edu.seu.student.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Student {
    @Id
    private Long id;
    private String name;
    private Double cgpa;
    private String loginPass;
    @Email
    private String email;
    private LocalDate dob;
    private LocalDate admissionDate;
    private Double crCompleted;
    private String program;
    private Status paymentStatus;
    private String trxID;
    private Double feePaid;
    private List<Course> coursList;

    public Student(Long id, String name, String loginPass, @Email String email, LocalDate dob, LocalDate admissionDate, String program) {
        this.id = id;
        this.name = name;
        this.loginPass = loginPass;
        this.email = email;
        this.dob = dob;
        this.admissionDate = admissionDate;
        this.program = program;
    }

    public void registerCourse(Course ... course){
        if(coursList == null)
            coursList = new ArrayList<>();

        Arrays.stream(course).forEach(tempCourse -> coursList.add(tempCourse));
    }

}
