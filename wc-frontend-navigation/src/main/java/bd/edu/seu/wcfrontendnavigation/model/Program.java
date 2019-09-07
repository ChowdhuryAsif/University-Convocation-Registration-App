package bd.edu.seu.wcfrontendnavigation.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"title"})
public class Program {
    private String title;
    private Double minReqCgpaForGraduation;
    private Double minCrReqForGraduation;
    private Coordinator coordinator;
    private List<Course> courseList;

    public Program(String title, Double minReqCgpaForGraduation, Double minCrReqForGraduation, Coordinator coordinator) {
        this.title = title;
        this.minReqCgpaForGraduation = minReqCgpaForGraduation;
        this.minCrReqForGraduation = minCrReqForGraduation;
        this.coordinator = coordinator;
    }

    public void addCourse(Course... course) {
        if (courseList == null)
            courseList = new ArrayList<>();

        Arrays.stream(course).forEach(tempCourse -> {
            tempCourse.setProgram(title);
            courseList.add(tempCourse);
        });
    }
}
