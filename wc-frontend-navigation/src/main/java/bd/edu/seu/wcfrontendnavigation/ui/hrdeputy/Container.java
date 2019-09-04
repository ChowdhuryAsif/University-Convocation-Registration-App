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
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;


public class Container extends VerticalLayout {


    public Container(EmployeeService employeeService) {

        FormLayout employeeDetailsForm = new FormLayout();
        TextField initialField = new TextField("Initial");
        TextField nameField = new TextField("Name");
        TextField loginPassField = new TextField("Login Pass");

        ComboBox<Role> comboBox = new ComboBox<>("Role");
        comboBox.setItems(Role.ADMISSION_OFFICER, Role.DEPUTY_REGISTRAR, Role.HR_DEPUTY_REGISTRAR, Role.COORDINATOR, Role.EXAM_OFFICER);
        comboBox.setPlaceholder("Employee Role");

        // Display an icon which can be clicked to clear the value:
        comboBox.setClearButtonVisible(true);

        Button sumbitButton = new Button("Submit", VaadinIcon.ADD_DOCK.create());
        sumbitButton.getStyle().set("marginLeft", "600px");
        sumbitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sumbitButton.getStyle().set("background-color", "#34c65d");

        employeeDetailsForm.add(initialField, nameField, loginPassField, comboBox);

        Grid<Employee> employeeGrid = new Grid<>(Employee.class);
        employeeGrid.setItems(employeeService.findAll());
        employeeGrid.setWidth("800px");
        employeeGrid.setColumns("initial", "name", "loginPass", "role");



        add(employeeDetailsForm, sumbitButton, employeeGrid);

    }
}
