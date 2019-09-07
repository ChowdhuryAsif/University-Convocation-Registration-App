package bd.edu.seu.wcfrontendnavigation.ui.coordinator;


import bd.edu.seu.wcfrontendnavigation.model.*;
import bd.edu.seu.wcfrontendnavigation.service.EmployeeService;
import bd.edu.seu.wcfrontendnavigation.service.ProgramService;
import bd.edu.seu.wcfrontendnavigation.service.StudentService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;


public class Container extends VerticalLayout {

    private Student globalStudent;
    private Grid<Student> studentGrid;
    private EmployeeService employeeService;
    private ProgramService programService;
    private StudentService studentService;
    private Course globalCourse;
    private Employee loggedCoordinator;
    private Dialog courseRegistrationDialog;

    public Container(Employee loggedEmployee, StudentService studentService, ProgramService programService, EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.programService = programService;
        this.studentService = studentService;
        this.loggedCoordinator = loggedEmployee;

        HorizontalLayout horizontalLayout = new HorizontalLayout();

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
//        studentGrid
//                .addComponentColumn(student -> registerCourse(student));   // don't know why it's not working

        studentGrid.addItemClickListener(item -> {
            registerCourse(item.getItem());
            courseRegistrationDialog.open();
        });

        studentGrid.setItems(studentService.findAll().stream().filter(student -> student.getProgram().equals(loggedEmployee.getProgram())));

        add(studentGrid);

    }

    private Component registerCourse(Student student) {
        Button button = new Button("Register");
        globalStudent = student;
        courseRegistrationDialog = new Dialog();
        Program program = programService.getProgram(loggedCoordinator.getProgram());
        List<Course> courseList = program.getCourseList();

        Div gridArea = new Div();
        gridArea.add(new Span("Student ID: " + student.getId()));
        Grid<Course> courseGrid = new Grid<>(Course.class);
        courseGrid.setItems(courseList);
        gridArea.add(courseGrid);
        gridArea.setWidth("800px");


        Button closeDialogeButton = new Button("Close", VaadinIcon.CLOSE_CIRCLE.create());
        Button registerButton = new Button("Confirm", VaadinIcon.CHECK_SQUARE_O.create());
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.getStyle().set("background-color", "#34c65d");

        globalCourse = new Course();
        courseGrid.addItemClickListener(item -> {
            globalCourse.setCode(item.getItem().getCode());
            globalCourse.setTitle(item.getItem().getTitle());
            globalCourse.setProgram(item.getItem().getProgram());
            globalCourse.setCreditHour(item.getItem().getCreditHour());

            Student serviceStudent = studentService.getStudent(globalStudent.getId().toString());
            List<Course> serviceStudentCoursList = serviceStudent.getCoursList();

            for (int i = 0; i < serviceStudentCoursList.size(); i++) {
                if (serviceStudentCoursList.get(i).getCode().equals(globalCourse.getCode())) {
                    Notification.show(globalStudent.getId() + " already registered in " + globalCourse.getCode());
                }
            }

        });

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.add(closeDialogeButton, registerButton);
        buttons.getStyle().set("margin-left", "250px");

        closeDialogeButton.addClickListener(clickEvent -> courseRegistrationDialog.close());

        registerButton.addClickListener(event -> {
            Student serviceStudent = studentService.getStudent(globalStudent.getId().toString());
            serviceStudent.registerCourse(globalCourse);
            studentService.updateStudent(serviceStudent.getId(), serviceStudent);
            Notification.show("Registered!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        courseRegistrationDialog.add(gridArea, buttons);

        button.addClickListener(event -> courseRegistrationDialog.open());

        return button;
    }

}
