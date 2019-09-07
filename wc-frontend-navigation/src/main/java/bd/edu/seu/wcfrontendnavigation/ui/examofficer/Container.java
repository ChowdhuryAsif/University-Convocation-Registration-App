package bd.edu.seu.wcfrontendnavigation.ui.examofficer;

import bd.edu.seu.wcfrontendnavigation.model.*;
import bd.edu.seu.wcfrontendnavigation.service.EmployeeService;
import bd.edu.seu.wcfrontendnavigation.service.StudentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import java.util.List;
import java.util.stream.Collectors;


public class Container extends VerticalLayout {

    private Student globalStudent;
    private Grid<Student> studentGrid;
    private EmployeeService employeeService;
    private StudentService studentService;
    private Employee loggedExamOfficer;
    private Dialog courseGradeDialog;
    private Grade globalGrade;
    private Double creditHour;
    private Double credit;

    public Container(Employee loggedEmployee, StudentService studentService, EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.studentService = studentService;
        this.loggedExamOfficer = loggedEmployee;

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        // Nav Menu==============
        Tabs menu = new Tabs();
        Tab addProgramTab = new Tab("Grade Students");
        Tab addCourseTab = new Tab("Convocation");
        menu.add(addProgramTab, addCourseTab);
        menu.setOrientation(Tabs.Orientation.VERTICAL);
        menu.setWidth("200px");

        studentGrid = new Grid<>();
        studentGrid
                .addColumn(Student::getId)
                .setHeader("Student ID");
        studentGrid
                .addColumn(Student::getName)
                .setHeader("Name");
        studentGrid
                .addColumn(Student::getCrCompleted)
                .setHeader("Credit Completed");
        studentGrid
                .addColumn(Student::getCgpa)
                .setHeader("CGPA");

        studentGrid.addItemClickListener(item -> {
            gradeCourse(item.getItem());
            courseGradeDialog.open();
        });

        studentGrid.setWidth("800px");

        studentGrid.setItems(studentService.findAll().stream().filter(student -> student.getProgram().equals(loggedEmployee.getProgram())).collect(Collectors.toList()));

        horizontalLayout.add(menu, studentGrid);

        add(horizontalLayout);

    }

    private void gradeCourse(Student student) {
        globalStudent = student;
        courseGradeDialog = new Dialog();
        globalGrade = new Grade();

        List<Course> registeredCourseList = globalStudent.getCoursList();

        Div gridArea = new Div();
        gridArea.add(new Span("Student ID: " + student.getId()));
        Grid<Course> courseGrid = new Grid<>(Course.class);
        courseGrid.setItems(registeredCourseList);
        gridArea.add(courseGrid);
        gridArea.setWidth("800px");


        ComboBox<Double> gradeComboBox = new ComboBox<>();
        gradeComboBox.setItems(4.0, 3.75, 3.50, 3.25, 3.00, 2.75, 2.50, 2.25, 2.00);
        gradeComboBox.setRequired(true);
        gradeComboBox.setLabel("Grade");
        gradeComboBox.setPlaceholder("e.i 3.50");
        gradeComboBox.getStyle().set("margin-top", "15px");


        Button closeDialogeButton = new Button("Close", VaadinIcon.CLOSE_CIRCLE.create());
        Button gradeCourseButton = new Button("Confirm", VaadinIcon.CHECK_SQUARE_O.create());
        gradeCourseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        gradeCourseButton.getStyle().set("background-color", "#34c65d");

        globalGrade = new Grade();
        courseGrid.addItemClickListener(course -> {
            globalGrade.setCourse(course.getItem());

            Student serviceStudent = studentService.getStudent(globalStudent.getId().toString());
            List<Grade> gradeListOfServiceStudent = serviceStudent.getGradeList();

            boolean gradedCase = false;
            for (int i = 0; i < gradeListOfServiceStudent.size(); i++) {
                if (gradeListOfServiceStudent.get(i).getCourse().getCode().equals(globalGrade.getCourse().getCode())) {
                    gradedCase = true;
                    gradeComboBox.setValue(gradeListOfServiceStudent.get(i).getGrade());

                }
            }
            if (!gradedCase)
                gradeComboBox.setValue(null);

        });
        gradeComboBox.addValueChangeListener(value -> globalGrade.setGrade(value.getValue()));

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.add(closeDialogeButton, gradeCourseButton);
        buttons.getStyle().set("margin-left", "250px");

        closeDialogeButton.addClickListener(clickEvent -> courseGradeDialog.close());

        gradeCourseButton.addClickListener(event -> gradeWithCgpa(globalStudent, globalGrade));

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(gridArea, gradeComboBox);

        courseGradeDialog.add(horizontalLayout, buttons);
        courseGradeDialog.setCloseOnOutsideClick(false);

    }

    private void gradeWithCgpa(Student student, Grade grade) {
        Student serviceStudent = studentService.getStudent(student.getId().toString());
        serviceStudent.gradeCourse(grade);

        List<Grade> gradeList = serviceStudent.getGradeList();

        creditHour = 0.0;
        credit = 0.0;

        gradeList.forEach(tempGrade -> {
            creditHour += (tempGrade.getCourse().getCreditHour() * tempGrade.getGrade());
            credit += tempGrade.getCourse().getCreditHour();
        });

        Double cgpa = creditHour / credit;
        serviceStudent.setCgpa(cgpa);
        serviceStudent.setCrCompleted(credit);


        Student updatedStudent = studentService.updateStudent(serviceStudent.getId(), serviceStudent);

        Notification.show(grade.getCourse().getCode() + " graded to " + updatedStudent.getId()).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }


}
