package bd.edu.seu.student.model;

import bd.edu.seu.student.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Student {
    @Id
    private Long id;
    private String name;
    private Double cgpa;
    private String loginPass;
    private Double crCompleted;
    private String program;
    private Status status;
    private String trxID;

    public Student(Long id, String name, String loginPass, Double cgpa, Double crCompleted, String program) {
        this.id = id;
        this.name = name;
        this.loginPass = loginPass;
        this.cgpa = cgpa;
        this.crCompleted = crCompleted;
        this.program = program;
    }

}
