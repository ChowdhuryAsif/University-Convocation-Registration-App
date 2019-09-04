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

            Container container = new Container(studentService, loggedStudent);
            body.add(container);
        }


        body.setHeight("460px");

        Footer footer = new Footer();


        add(header, body, footer);
    }
}
