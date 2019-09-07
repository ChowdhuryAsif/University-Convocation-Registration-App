package bd.edu.seu.wcfrontendnavigation.ui.coordinator;

import bd.edu.seu.wcfrontendnavigation.enums.Role;
import bd.edu.seu.wcfrontendnavigation.model.Employee;
import bd.edu.seu.wcfrontendnavigation.model.LoginToken;
import bd.edu.seu.wcfrontendnavigation.service.EmployeeService;
import bd.edu.seu.wcfrontendnavigation.service.ProgramService;
import bd.edu.seu.wcfrontendnavigation.service.StudentService;
import bd.edu.seu.wcfrontendnavigation.ui.Footer;
import bd.edu.seu.wcfrontendnavigation.ui.Header;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.servlet.http.HttpSession;

@Route("coordinator")
public class CoordinatorView extends VerticalLayout {

    private LoginToken loginToken;
    private EmployeeService employeeService;
    private ProgramService programService;
    private StudentService studentService;

    public CoordinatorView(StudentService studentService, ProgramService programService, EmployeeService employeeService, HttpSession httpSession) {
        super();
        this.employeeService = employeeService;
        this.programService = programService;
        this.studentService = studentService;

        Header header = new Header(httpSession);
        header.addAttachListener(event -> {
            LoginToken loginToken = header.getLoginToken();
            if(!loginToken.getRole().equals(Role.COORDINATOR)){
                httpSession.removeAttribute("user");
                header.getUI().ifPresent(ui -> ui.navigate("login"));
            }else{
                header.setFullNameLabel("Welcome, Coordinator");
            }
        });

        Div body = new Div();

        loginToken = (LoginToken) httpSession.getAttribute("user");
        if(loginToken == null || !loginToken.getRole().equals(Role.COORDINATOR)){
            httpSession.removeAttribute("user");
            header.getUI().ifPresent(ui -> ui.navigate("login"));
        }
        else{
            String username = loginToken.getUsername();
            Employee employee = employeeService.getEmployee(username);
            Container container = new Container(employee, studentService, programService, employeeService);

            body.add(container);
        }



        body.setWidth("800px");
        body.setHeight("460px");

        Footer footer = new Footer();

        add(header, body, footer);
    }
}
