package bd.edu.seu.wcfrontendnavigation.ui.admission;

import bd.edu.seu.wcfrontendnavigation.enums.Role;
import bd.edu.seu.wcfrontendnavigation.model.LoginToken;
import bd.edu.seu.wcfrontendnavigation.service.StudentService;
import bd.edu.seu.wcfrontendnavigation.ui.Footer;
import bd.edu.seu.wcfrontendnavigation.ui.Header;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.servlet.http.HttpSession;

@Route("admission-officer")
public class AdmissionOfficerView extends VerticalLayout {

    private LoginToken loginToken;
    private StudentService studentService;
    public AdmissionOfficerView(StudentService studentService, HttpSession httpSession) {
        super();
        this.studentService = studentService;

        Header header = new Header(httpSession);
        header.addAttachListener(event -> {
            LoginToken loginToken = header.getLoginToken();
            if(!loginToken.getRole().equals(Role.ADMISSION_OFFICER)){
                httpSession.removeAttribute("user");
                header.getUI().ifPresent(ui -> ui.navigate("login"));
            }
            else{
                header.setFullNameLabel("Admission Officer");
            }
        });

        Div body = new Div();

        loginToken = (LoginToken) httpSession.getAttribute("user");
        if(loginToken == null){
            httpSession.removeAttribute("user");
            header.getUI().ifPresent(ui -> ui.navigate("login"));
        }
        else{

            Container container = new Container(studentService);

            body.add(container);
        }



        body.setWidth("800px");
        body.setHeight("460px");

        Footer footer = new Footer();

        add(header, body, footer);
    }
}
