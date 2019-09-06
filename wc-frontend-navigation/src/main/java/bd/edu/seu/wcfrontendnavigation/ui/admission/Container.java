package bd.edu.seu.wcfrontendnavigation.ui.admission;
import bd.edu.seu.wcfrontendnavigation.model.Student;
import bd.edu.seu.wcfrontendnavigation.service.StudentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.LongRangeValidator;
import com.vaadin.flow.data.validator.RegexpValidator;

import java.time.LocalDate;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.DAYS;

public class Container extends VerticalLayout {

    private StudentService studentService;
    private Dialog addStudentDialog;
    private Binder<Student> studentBinder;
    private Pattern pattern;
    private RegexpValidator regexpValidator;
    private Student globalStudent;


    public Container(StudentService studentService) {
        super();
        this.studentService = studentService;
        studentBinder = new Binder<>();
        pattern = Pattern.compile("[^A-Za-z ]");

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        // Nav Menu==============
        Tabs menu = new Tabs();
        Tab homeTab = new Tab("Home");
        Tab addStudent = new Tab("Add Student");
        menu.add(homeTab, addStudent);
        menu.setOrientation(Tabs.Orientation.VERTICAL);

        addStudentDialog = new Dialog();
        addStudentDialog.add(addStudentForm());


        menu.addSelectedChangeListener(menus -> {
            String tab = menus.getSelectedTab().getLabel();
            if(tab.equals("Add Student")){
                addStudentDialog.open();
                addStudentDialog.setCloseOnOutsideClick(false);
            }
            else {
                //something in future===========
            }
        });

        add(menu);
    }

    private VerticalLayout addStudentForm() {
        FormLayout studentForm = new FormLayout();
        TextField idField = new TextField("Student ID");
        TextField nameField = new TextField("Full Name");
        EmailField emailField = new EmailField("Email Address");
        TextField programField = new TextField("Program");
        DatePicker dobField = new DatePicker("Date of Birth");
        DatePicker admissionDateField = new DatePicker("Admission Date");
        TextField loginPassField = new TextField("Initial Login Pass");
        studentForm.add(idField, nameField, emailField, dobField, programField, admissionDateField, loginPassField);

        bindStudentForm(idField, nameField, emailField, programField, dobField, admissionDateField, loginPassField);

        Button closeDialogeButton = new Button("Close", VaadinIcon.CLOSE_CIRCLE.create());
        Button addButton = new Button("Confirm Admision", VaadinIcon.PLUS_CIRCLE_O.create());
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.getStyle().set("background-color", "#34c65d");
        Button resetButton = new Button("Reset", VaadinIcon.REFRESH.create());
        resetButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.add(closeDialogeButton, addButton, resetButton);
        buttons.getStyle().set("margin-left", "800px");

        closeDialogeButton.addClickListener(event -> addStudentDialog.close());

        addButton.addClickListener(event -> {
            globalStudent = new Student();
            try {
                studentBinder.writeBean(globalStudent);
                Student insertedStudent = studentService.insertStudent(globalStudent);
                Notification.show(insertedStudent.getId() + " admitted").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                clearField(idField, nameField, emailField, loginPassField, dobField, admissionDateField, programField);
            } catch (ValidationException e) {
                Notification.show(e.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (Exception e){
                Notification.show(e.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        resetButton.addClickListener(event -> clearField(idField, nameField, emailField, loginPassField, dobField, admissionDateField, programField));

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(studentForm, buttons);

        return verticalLayout;
    }

    private void clearField(TextField idField, TextField nameField, EmailField emailField, TextField loginPassField, DatePicker dobField, DatePicker admissionDateField, TextField programField) {
        idField.setValue("");
        programField.setValue("");
        nameField.setValue("");
        emailField.setValue("");
        dobField.setValue(null);
        admissionDateField.setValue(null);
        loginPassField.setValue("");
    }

    private void bindStudentForm(TextField idField, TextField nameField, EmailField emailField, TextField programField, DatePicker dobField, DatePicker admissionDateField, TextField loginPassField) {
        studentBinder
                .forField(idField)
                .asRequired()
                .withConverter(new StringToLongConverter("Student ID must be numerical"))
                .bind(Student::getId, Student::setId);

        studentBinder
                .forField(nameField)
                .asRequired()
                .withValidator(name -> !pattern.matcher(name).find(), "Name should not contain any spacial character.")
                .withValidator(name -> name.length() >= 6, "Name should be greater than 6 character.")
                .withValidator(name -> name.length() <= 60, "Name should not be greater than 60 charater.")
                .bind(Student::getName, Student::setName);

        studentBinder
                .forField(emailField)
                .asRequired()
                .withValidator(email -> email.endsWith(".com"), "email should be like 'example@emple.com'")
                .withValidator(email -> email.contains("@"), "email should be like 'example@emple.com'")
                .bind(Student::getEmail, Student::setEmail);

        studentBinder
                .forField(programField)
                .asRequired()
                .bind(Student::getProgram, Student::setProgram);

        studentBinder
                .forField(dobField)
                .asRequired()
                .withValidator(dateOfBirth -> DAYS.between(dateOfBirth, LocalDate.now()) > 365 * 18, "Student should be at least 18 years old.")
                .bind(Student::getDob, Student::setDob);

        studentBinder
                .forField(admissionDateField)
                .asRequired()
                .bind(Student::getAdmissionDate, Student::setAdmissionDate);

        studentBinder
                .forField(loginPassField)
                .asRequired()
                .bind(Student::getLoginPass, Student::setLoginPass);
    }
}
