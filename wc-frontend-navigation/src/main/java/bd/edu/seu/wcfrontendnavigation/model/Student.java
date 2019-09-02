package bd.edu.seu.wcfrontendnavigation.model;

import bd.edu.seu.wcfrontendnavigation.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Student {
    private Long id;
    private String name;
    private Double cgpa;
    private String loginPass;
    private Double crCompleted;
    private String program;
    private Status status;
    private String trxID;
    private Double feePaid;

    public Student(Long id, String name, String loginPass, Double cgpa, Double crCompleted, String program) {
        this.id = id;
        this.name = name;
        this.loginPass = loginPass;
        this.cgpa = cgpa;
        this.crCompleted = crCompleted;
        this.program = program;
    }

}
