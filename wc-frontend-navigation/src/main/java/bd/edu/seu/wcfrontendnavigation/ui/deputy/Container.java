package bd.edu.seu.wcfrontendnavigation.ui.deputy;

import bd.edu.seu.wcfrontendnavigation.enums.Role;
import bd.edu.seu.wcfrontendnavigation.model.Coordinator;
import bd.edu.seu.wcfrontendnavigation.model.Course;
import bd.edu.seu.wcfrontendnavigation.model.Employee;
import bd.edu.seu.wcfrontendnavigation.model.Program;
import bd.edu.seu.wcfrontendnavigation.service.EmployeeService;
import bd.edu.seu.wcfrontendnavigation.service.ProgramService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToDoubleConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Container extends VerticalLayout {

    private ProgramService programService;
    private Dialog programDialog;
    private Dialog courseDialog;
    private Dialog programToCourseDialog;
    private EmployeeService employeeService;
    private Binder<Program> programBinder;
    private Binder<Course> courseBinder;
    private Program globalProgram;
    private Course globalCourse;
    private Grid<Program> programGrid;


    public Container(EmployeeService employeeService, ProgramService programService) {
        super();
        this.programService = programService;
        this.employeeService = employeeService;

        programBinder = new Binder<>();
        courseBinder = new Binder<>();

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        // Nav Menu==============
        Tabs menu = new Tabs();
        Tab homeTab = new Tab("Home");
        Tab addProgramTab = new Tab("Add Program");
        Tab addCourseTab = new Tab("Add Course");
        menu.add(homeTab, addProgramTab, addCourseTab);
        menu.setOrientation(Tabs.Orientation.VERTICAL);
        menu.setWidth("200px");

        programGrid = new Grid<>();
        Grid.Column<Program> programTitle = programGrid
                .addColumn(Program::getTitle)
                .setHeader("Program")
                .setTextAlign(ColumnTextAlign.CENTER);
        Grid.Column<Program> creditRequired = programGrid
                .addColumn(Program::getMinCrReqForGraduation)
                .setHeader("Credit")
                .setTextAlign(ColumnTextAlign.CENTER);
        Grid.Column<Program> cgpaRequired = programGrid
                .addColumn(Program::getMinReqCgpaForGraduation)
                .setHeader("CGPA")
                .setTextAlign(ColumnTextAlign.CENTER);
        Grid.Column<Program> coordinator = programGrid
                .addColumn(program -> program.getCoordinator().getName())
                .setHeader("Coordinator")
                .setTextAlign(ColumnTextAlign.CENTER);

        HeaderRow headerRow = programGrid.prependHeaderRow();

        Div requirment = new Div(new Span("Requirment for Graduation"));
        requirment.getStyle().set("text-align", "center");
        requirment.setSizeFull();
        headerRow.join(creditRequired, cgpaRequired).setComponent(requirment);

        programGrid.setWidth("1000px");
        programGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);
        programGrid.setItems(programService.findAll());

        programGrid.addItemClickListener(item -> {
            List<Course> courseList = item.getItem().getCourseList();

            programToCourseDialog = new Dialog();
            programToCourseDialog.add(openProgramDetails(item.getItem().getTitle(), courseList));
            programToCourseDialog.open();
        });


        programDialog = new Dialog();
        programDialog.add(programForm());

        courseDialog = new Dialog();
        courseDialog.add(courseForm());


        menu.addSelectedChangeListener(menus -> {
            String tab = menus.getSelectedTab().getLabel();
            if (tab.equals("Add Program")) {
                programDialog.open();
                programDialog.setCloseOnOutsideClick(false);
            } else if (tab.equals("Add Course")) {
                courseDialog.open();
                courseDialog.setCloseOnOutsideClick(false);
            } else {

            }
        });
        menu.getStyle().set("alignment", "center");

        horizontalLayout.add(menu, programGrid);
        add(horizontalLayout);

    }

    private Div openProgramDetails(String programTitle, List<Course> courseList) {
        Div div = new Div();
        Label programName = new Label();
        programName.setText("Program: " + programTitle);

        Grid<Course> courseGrid = new Grid<>(Course.class);
        courseGrid.setItems(courseList);
        courseGrid.getStyle().set("text-align", "center");

        div.add(programName, courseGrid);
        div.setWidth("700px");

        return div;
    }

    private HorizontalLayout courseForm() {

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        VerticalLayout verticalLayout = new VerticalLayout();

        FormLayout addCourseForm = new FormLayout();
        TextField codeField = new TextField("Course Code");
        TextField titleField = new TextField("Course Title");
        TextField crHourField = new TextField("Credit Hour");
        addCourseForm.add(codeField, titleField, crHourField);

        Button closeDialogeButton = new Button("Close", VaadinIcon.CLOSE_CIRCLE.create());
        Button addButton = new Button("Add", VaadinIcon.PLUS_CIRCLE_O.create());
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.getStyle().set("background-color", "#34c65d");
        Button resetButton = new Button("Reset", VaadinIcon.REFRESH.create());
        resetButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.add(closeDialogeButton, addButton, resetButton);

        ComboBox<String> programComboBox = new ComboBox<>();
        programComboBox.setPlaceholder("Programs");
        programComboBox.setRequired(true);
        programComboBox.setLabel("Programs");
        programComboBox.getStyle().set("margin-top", "15px");
        List<Program> programList = programService.findAll();

        List<String> programTitleList = new ArrayList<>();
        programList.forEach(program -> programTitleList.add(program.getTitle()));
        programComboBox.setItems(programTitleList);

        bindCourseField(codeField, titleField, crHourField);

        globalCourse = new Course();
        programComboBox.addValueChangeListener(program -> globalCourse.setProgram(program.getValue()));

        addButton.addClickListener(event -> {
            try {
                courseBinder.writeBean(globalCourse);
                Program program = programService.getProgram(globalCourse.getProgram());
                program.addCourse(globalCourse);
                Program updatedProgram = programService.updateProgram(program.getTitle(), program);
                Notification.show(globalCourse.getCode() + " added").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } catch (ValidationException e) {
                Notification.show(e.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (Exception e) {
                Notification.show(e.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }

        });

        closeDialogeButton.addClickListener(event -> courseDialog.close());
        verticalLayout.add(addCourseForm, buttons);

        horizontalLayout.add(verticalLayout, programComboBox);
        return horizontalLayout;
    }

    private void bindCourseField(TextField codeField, TextField titleField, TextField crHourField) {

        courseBinder
                .forField(codeField)
                .asRequired()
                .bind(Course::getCode, Course::setCode);

        courseBinder
                .forField(titleField)
                .asRequired()
                .bind(Course::getTitle, Course::setTitle);

        courseBinder
                .forField(crHourField)
                .asRequired()
                .withConverter(new StringToDoubleConverter("Credit must be a Decimal value"))
                .bind(Course::getCreditHour, Course::setCreditHour);
    }

    public HorizontalLayout programForm() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        VerticalLayout verticalLayout = new VerticalLayout();

        FormLayout programForm = new FormLayout();
        TextField programNameField = new TextField("Name of Program");
        TextField crReqField = new TextField("Minimum Credit Required for Graduation");
        TextField cgRegField = new TextField("Minimum CGPA Required for Graduation");

        bindProgramField(programNameField, crReqField, cgRegField);

        programForm.add(programNameField, crReqField, cgRegField);

        Button closeDialogeButton = new Button("Close", VaadinIcon.CLOSE_CIRCLE.create());
        Button addButton = new Button("Save", VaadinIcon.ADD_DOCK.create());
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.getStyle().set("background-color", "#34c65d");
        Button resetButton = new Button("Reset", VaadinIcon.REFRESH.create());
        resetButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.add(closeDialogeButton, addButton, resetButton);

        closeDialogeButton.addClickListener(event -> programDialog.close());
        verticalLayout.add(programForm, buttons);

        ComboBox<String> coordinatorComboBox = new ComboBox<>();
        coordinatorComboBox.setPlaceholder("Coordinators");
        coordinatorComboBox.getStyle().set("margin-top", "15px");
        coordinatorComboBox.setRequired(true);
        coordinatorComboBox.setLabel("Coordinators");

        List<Employee> coordinatorList = employeeService
                .findAll()
                .stream()
                .filter(predicate -> predicate.getRole().equals(Role.COORDINATOR))
                .collect(Collectors.toList());

        List<String> coordinatorInitials = new ArrayList<>();
        coordinatorList.forEach(coordinator -> coordinatorInitials.add(coordinator.getInitial()));
        coordinatorComboBox.setItems(coordinatorInitials);

        globalProgram = new Program();
        coordinatorComboBox.addValueChangeListener(coordinator -> {
            Employee employee = employeeService.getEmployee(coordinator.getValue());
            Coordinator programCoordinator = new Coordinator();
            programCoordinator.setInitial(employee.getInitial());
            programCoordinator.setName(employee.getName());
            programCoordinator.setLoginPass(employee.getLoginPass());
            programCoordinator.setRole(employee.getRole());
            globalProgram.setCoordinator(programCoordinator);
        });
        addButton.addClickListener(event -> {

            try {
                programBinder.writeBean(globalProgram);
                Program insertedProgram = programService.insertProgram(globalProgram);
                Notification.show(insertedProgram.getTitle() + " saved.").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                programGrid.setItems(programService.findAll());
            } catch (ValidationException e) {
                Notification.show(e.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (Exception e) {
                Notification.show(e.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        horizontalLayout.add(verticalLayout, coordinatorComboBox);
        return horizontalLayout;
    }

    private void bindProgramField(TextField programNameField, TextField crReqField, TextField cgRegField) {
        programBinder
                .forField(programNameField)
                .asRequired()
                .bind(Program::getTitle, Program::setTitle);

        programBinder
                .forField(crReqField)
                .asRequired()
                .withConverter(new StringToDoubleConverter("Credit must be a Decimal value"))
                .bind(Program::getMinCrReqForGraduation, Program::setMinCrReqForGraduation);

        programBinder
                .forField(cgRegField)
                .asRequired()
                .withConverter(new StringToDoubleConverter("CGPA must be a Decimal value"))
                .bind(Program::getMinReqCgpaForGraduation, Program::setMinReqCgpaForGraduation);
    }

}
