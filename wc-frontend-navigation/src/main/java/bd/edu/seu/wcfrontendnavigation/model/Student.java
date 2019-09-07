package bd.edu.seu.wcfrontendnavigation.model;

import bd.edu.seu.wcfrontendnavigation.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private LocalDate dob;
    private LocalDate admissionDate;
    private Double crCompleted;
    private String program;
    private Status paymentStatus;
    private String trxID;
    private Double feePaid;
    private List<Course> coursList;
    private List<Grade> gradeList;

    public Student(Long id, String name, String loginPass, String email, LocalDate dob, LocalDate admissionDate, String program) {
        this.id = id;
        this.name = name;
        this.loginPass = loginPass;
        this.email = email;
        this.dob = dob;
        this.admissionDate = admissionDate;
        this.program = program;
    }

    public void registerCourse(Course ... courses){
        if(coursList == null)
            coursList = new ArrayList<>();

        Arrays.stream(courses).forEach(course -> coursList.add(course));
    }

    public void gradeCourse(Grade ... grades){
        if(gradeList == null)
            gradeList = new ArrayList<>();

        Arrays.stream(grades).forEach(grade -> gradeList.add(grade));
    }

}
