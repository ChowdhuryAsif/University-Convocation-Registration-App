package bd.edu.seu.wcfrontendnavigation.ui.student;

import bd.edu.seu.wcfrontendnavigation.enums.Role;
import bd.edu.seu.wcfrontendnavigation.model.LoginToken;
import bd.edu.seu.wcfrontendnavigation.model.Student;
import bd.edu.seu.wcfrontendnavigation.service.StudentService;
import bd.edu.seu.wcfrontendnavigation.ui.Footer;
import bd.edu.seu.wcfrontendnavigation.ui.Header;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import javax.servlet.http.HttpSession;

@Route("student")
public class StudentView extends VerticalLayout {

    private StudentService studentService;
    private LoginToken loginToken;
    private Student student;

    public StudentView(StudentService studentService, HttpSession httpSession) {
        super();
        this.studentService = studentService;


        Header header = new Header(httpSession);

        header.addAttachListener(event -> {
            loginToken = header.getLoginToken();
            if(!loginToken.getRole().equals(Role.STUDENT) || loginToken == null){
                httpSession.removeAttribute("user");
                header.getUI().ifPresent(ui -> ui.navigate("login"));
            }
            else{
                student = studentService.getStudent(loginToken.getUsername());
                header.setFullNameLabel(this.student.getName());
            }
        });

        Div body = new Div();

        loginToken = (LoginToken) httpSession.getAttribute("user");
        if(loginToken == null){
            httpSession.removeAttribute("user");
            header.getUI().ifPresent(ui -> ui.navigate("login"));
        }
        else{
            Student loggedStudent = studentService.getStudent(loginToken.getUsername());

            HorizontalLayout horizontalLayout = new HorizontalLayout();

            //SideBar Nav Menu==============
            Tabs menu = new Tabs();
            Tab homeTab = new Tab("Home");
            Tab infoTab = new Tab("Info");
            Tab paymentTab = new Tab("Payment");
            menu.add(homeTab, infoTab, paymentTab);

            menu.setOrientation(Tabs.Orientation.VERTICAL);
            menu.setWidth("200px");

            //ApplicationStatusForm==========
            FormLayout applicationStatusForm = new FormLayout();
            TextField userIdField = new TextField("UserId");
            TextField feesDueField = new TextField("Fees To Pay");
            TextField feesPayedField = new TextField("Paid");
            TextField applicationStatusField = new TextField("Status");
            applicationStatusForm.add(userIdField, feesDueField, feesPayedField, applicationStatusField);

            userIdField.setValue(loggedStudent.getId().toString());
            feesDueField.setValue("Registration Fee");
            if(loggedStudent.getFeePaid() != null)
                feesPayedField.setValue(loggedStudent.getFeePaid().toString().trim());
            if(loggedStudent.getPaymentStatus() != null)
                applicationStatusField.setValue(loggedStudent.getPaymentStatus().toString().trim());

            horizontalLayout.add(menu, applicationStatusForm);
            body.add(horizontalLayout);

            menu.addSelectedChangeListener(menus -> {
                String tab = menus.getSelectedTab().getLabel();
                if(tab.equals("Info")){
                    horizontalLayout.remove(applicationStatusForm);
                }
                else if(tab.equals("Payment")){
                    horizontalLayout.remove(applicationStatusForm);
                }
                else {
                    horizontalLayout.add(applicationStatusForm);
                }
            });
        }


        body.setHeight("460px");

        Footer footer = new Footer();


        add(header, body, footer);
    }
}
