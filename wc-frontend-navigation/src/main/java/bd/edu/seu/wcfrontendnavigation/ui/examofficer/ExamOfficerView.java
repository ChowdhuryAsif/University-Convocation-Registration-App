package bd.edu.seu.wcfrontendnavigation.ui.examofficer;

import bd.edu.seu.wcfrontendnavigation.enums.Role;
import bd.edu.seu.wcfrontendnavigation.model.LoginToken;
import bd.edu.seu.wcfrontendnavigation.model.Student;
import bd.edu.seu.wcfrontendnavigation.service.ProgramService;
import bd.edu.seu.wcfrontendnavigation.service.StudentService;
import bd.edu.seu.wcfrontendnavigation.ui.Footer;
import bd.edu.seu.wcfrontendnavigation.ui.Header;
import bd.edu.seu.wcfrontendnavigation.ui.student.Container;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.servlet.http.HttpSession;

@Route("exam-officer")
public class ExamOfficerView extends VerticalLayout {
    private LoginToken loginToken;

    public ExamOfficerView(HttpSession httpSession) {
        super();



        Header header = new Header(httpSession);

        header.addAttachListener(event -> {
            loginToken = header.getLoginToken();
            if(!loginToken.getRole().equals(Role.EXAM_OFFICER) || loginToken == null){
                httpSession.removeAttribute("user");
                header.getUI().ifPresent(ui -> ui.navigate("login"));
            }
            else{
                header.setFullNameLabel("Exam Officer");
            }
        });

        Div body = new Div();

        loginToken = (LoginToken) httpSession.getAttribute("user");
        if(loginToken == null || !loginToken.getRole().equals(Role.EXAM_OFFICER)){
            httpSession.removeAttribute("user");
            header.getUI().ifPresent(ui -> ui.navigate("login"));
        }
        else{

            // container goes here ==============
        }


        body.setHeight("460px");

        Footer footer = new Footer();


        add(header, body, footer);
    }
}
