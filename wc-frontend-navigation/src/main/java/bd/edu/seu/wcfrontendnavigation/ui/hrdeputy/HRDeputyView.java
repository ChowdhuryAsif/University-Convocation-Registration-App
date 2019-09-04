package bd.edu.seu.wcfrontendnavigation.ui.hrdeputy;

import bd.edu.seu.wcfrontendnavigation.enums.Role;
import bd.edu.seu.wcfrontendnavigation.model.LoginToken;
import bd.edu.seu.wcfrontendnavigation.model.Student;
import bd.edu.seu.wcfrontendnavigation.ui.Footer;
import bd.edu.seu.wcfrontendnavigation.ui.Header;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.servlet.http.HttpSession;

@Route("hr-deputy")
public class HRDeputyView extends VerticalLayout {

    private LoginToken loginToken;

    public HRDeputyView(HttpSession httpSession) {
        super();

        Header header = new Header(httpSession);
        header.addAttachListener(event -> {
            LoginToken loginToken = header.getLoginToken();
            if(!loginToken.getRole().equals(Role.HR_DEPUTY_REGISTRAR)){
                httpSession.removeAttribute("user");
                header.getUI().ifPresent(ui -> ui.navigate("login"));
            }else{
                header.setFullNameLabel("Deputy Registrar (Human Resource)");
            }
        });

        Div body = new Div();

        loginToken = (LoginToken) httpSession.getAttribute("user");
        if(loginToken == null){
            httpSession.removeAttribute("user");
            header.getUI().ifPresent(ui -> ui.navigate("login"));
        }
        else{

            Container container = new Container();
            body.add(container);
        }


        body.setHeight("460px");

        Footer footer = new Footer();

        add(header, body, footer);
    }
}
