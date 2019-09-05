package bd.edu.seu.wcfrontendnavigation.ui.deputy;

import bd.edu.seu.wcfrontendnavigation.enums.Role;
import bd.edu.seu.wcfrontendnavigation.model.LoginToken;
import bd.edu.seu.wcfrontendnavigation.service.EmployeeService;
import bd.edu.seu.wcfrontendnavigation.service.ProgramService;
import bd.edu.seu.wcfrontendnavigation.ui.Footer;
import bd.edu.seu.wcfrontendnavigation.ui.Header;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.servlet.http.HttpSession;

@Route("deputy-registrar")
public class DeputyRegistrarView extends VerticalLayout {

    private LoginToken loginToken;
    private ProgramService programService;
    private EmployeeService employeeService;

    public DeputyRegistrarView(ProgramService programService, EmployeeService employeeService, HttpSession httpSession) {
        super();
        this.programService = programService;
        this.employeeService = employeeService;

        Header header = new Header(httpSession);
        header.addAttachListener(event -> {
            LoginToken loginToken = header.getLoginToken();
            if(!loginToken.getRole().equals(Role.DEPUTY_REGISTRAR)){
                httpSession.removeAttribute("user");
                header.getUI().ifPresent(ui -> ui.navigate("login"));
            }
            else{
                header.setFullNameLabel("Deputy Registrar");
            }
        });

        Div body = new Div();

        loginToken = (LoginToken) httpSession.getAttribute("user");
        if(loginToken == null){
            httpSession.removeAttribute("user");
            header.getUI().ifPresent(ui -> ui.navigate("login"));
        }
        else{

            Container container = new Container(employeeService, programService);

            body.add(container);
        }



        body.setWidth("800px");
        body.setHeight("460px");

        Footer footer = new Footer();

        add(header, body, footer);
    }
}
