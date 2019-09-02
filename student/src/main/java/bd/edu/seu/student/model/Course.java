package bd.edu.seu.student.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"code"})
public class Course {
    @Id
    private String code;
    private String title;
    private Double creditHour;
    private String program;

    public Course(String code, String title, Double creditHour) {
        this.code = code;
        this.title = title;
        this.creditHour = creditHour;
    }

}
