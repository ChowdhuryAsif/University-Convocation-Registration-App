package bd.edu.seu.wcfrontendnavigation.ui.hrdeputy;

import bd.edu.seu.wcfrontendnavigation.enums.Role;
import bd.edu.seu.wcfrontendnavigation.model.Employee;
import bd.edu.seu.wcfrontendnavigation.service.EmployeeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;



public class Container extends VerticalLayout {

    private Binder<Employee> employeeBinder;
    private Employee employee;
    private Employee insertedEmployee;
    private Grid<Employee> employeeGrid;
    private EmployeeService employeeService;

    public Container(EmployeeService employeeService) {
        this.employeeService = employeeService;

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        employeeBinder = new Binder<>();

        FormLayout employeeDetailsForm = new FormLayout();
        TextField initialField = new TextField("Initial");
        TextField nameField = new TextField("Name");
        TextField loginPassField = new TextField("Login Pass");

        employeeBinder
                .forField(initialField)
                .asRequired()
                .bind(Employee::getInitial, Employee::setInitial);

        employeeBinder
                .forField(nameField)
                .asRequired()
                .bind(Employee::getName, Employee::setName);

        employeeBinder
                .forField(loginPassField)
                .asRequired()
                .bind(Employee::getLoginPass, Employee::setLoginPass);

        ComboBox<Role> comboBox = new ComboBox<>("Role");
        comboBox.setItems(Role.ADMISSION_OFFICER, Role.DEPUTY_REGISTRAR, Role.HR_DEPUTY_REGISTRAR, Role.COORDINATOR, Role.EXAM_OFFICER);
        comboBox.setPlaceholder("Employee Role");
        comboBox.setClearButtonVisible(true);
        comboBox.setRequired(true);


        Button sumbitButton = new Button("Submit", VaadinIcon.ADD_DOCK.create());
        sumbitButton.getStyle().set("marginLeft", "600px");
        sumbitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sumbitButton.getStyle().set("background-color", "#34c65d");
        sumbitButton.getStyle().set("margin-top", "20px");

        employeeDetailsForm.add(initialField, nameField, loginPassField, comboBox, sumbitButton);
        employeeDetailsForm.setWidth("300px");
        employeeDetailsForm.getStyle().set("margin-left", "100px");

        employeeGrid = new Grid<>(Employee.class);
        employeeGrid.setItems(employeeService.findAll());
        employeeGrid.setWidth("800px");
        employeeGrid.setColumns("initial", "name", "loginPass", "role");
        employeeGrid.getStyle().set("margin-left", "50px");





        employee = new Employee();
        comboBox.addValueChangeListener(role -> this.employee.setRole(role.getValue()));
        sumbitButton.addClickListener(event -> {

            try {
                employeeBinder.writeBean(employee);
                Employee insertedEmployee = employeeService.insertEmployee(employee);
                employeeGrid.setItems(employeeService.findAll());
                Notification.show(insertedEmployee.getName() + " inserted!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            } catch (ValidationException e) {
                Notification.show(e.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (Exception e) {
                Notification.show(e.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        horizontalLayout.add(employeeDetailsForm, employeeGrid);

        add(horizontalLayout);

    }
}
