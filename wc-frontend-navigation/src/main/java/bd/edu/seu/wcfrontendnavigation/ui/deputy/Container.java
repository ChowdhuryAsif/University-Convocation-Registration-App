package bd.edu.seu.wcfrontendnavigation.ui.deputy;

import bd.edu.seu.wcfrontendnavigation.enums.Role;
import bd.edu.seu.wcfrontendnavigation.model.Coordinator;
import bd.edu.seu.wcfrontendnavigation.model.Employee;
import bd.edu.seu.wcfrontendnavigation.service.EmployeeService;
import bd.edu.seu.wcfrontendnavigation.service.ProgramService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class Container extends VerticalLayout {

    private ProgramService programService;
    private Dialog programDialog;
    private Dialog courseDialog;
    private EmployeeService employeeService;
    //private ComboBox<Coordinator> coordinatorComboBox;

    public Container(EmployeeService employeeService, ProgramService programService) {
        this.programService = programService;
        this.employeeService = employeeService;

        //HorizontalLayout horizontalLayout = new HorizontalLayout();

        //SideBar Nav Menu==============
        Tabs menu = new Tabs();
        Tab homeTab = new Tab("Home");
        Tab addProgramTab = new Tab("Add Program");
        Tab addCourseTab = new Tab("Add Course");
        menu.add(homeTab, addProgramTab, addCourseTab);




        programDialog = new Dialog();
        programDialog.add(programForm());

        courseDialog = new Dialog();


        menu.addSelectedChangeListener(menus -> {
            String tab = menus.getSelectedTab().getLabel();
            if(tab.equals("Add Program")){
                programDialog.open();
                programDialog.setCloseOnOutsideClick(false);
            }
            else if(tab.equals("Add Course")){

            }
            else {

            }
        });
        menu.getStyle().set("alignment", "center");

        add(menu);

    }

    public HorizontalLayout programForm(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        VerticalLayout verticalLayout = new VerticalLayout();

        FormLayout programForm = new FormLayout();
        TextField programNameField = new TextField("Name of Program");
        TextField crReqField = new TextField("Minimum Credit Required for Graduation");
        TextField cgRegField = new TextField("Minimum CGPA Required for Graduation");

        programForm.add(programNameField, crReqField, cgRegField);
        Button closeDialogeButton = new Button("Close", VaadinIcon.CLOSE_CIRCLE.create());

        closeDialogeButton.addClickListener(event -> programDialog.close());
        verticalLayout.add(programForm, closeDialogeButton);

        ComboBox<String> coordinatorComboBox = new ComboBox<>();

        List<Employee> coordinatorList = employeeService
                                                    .findAll()
                                                    .stream()
                                                    .filter(predicate -> predicate.getRole().equals(Role.COORDINATOR))
                                                    .collect(Collectors.toList());

        coordinatorList.forEach(employee -> coordinatorComboBox.setItems(employee.getInitial()));

        horizontalLayout.add(verticalLayout, coordinatorComboBox);
        return horizontalLayout;
    }
}
