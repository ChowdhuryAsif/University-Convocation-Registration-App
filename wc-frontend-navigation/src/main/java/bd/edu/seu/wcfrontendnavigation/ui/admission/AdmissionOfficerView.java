package bd.edu.seu.wcfrontendnavigation.ui.admission;

import bd.edu.seu.wcfrontendnavigation.enums.Role;
import bd.edu.seu.wcfrontendnavigation.model.LoginToken;
import bd.edu.seu.wcfrontendnavigation.ui.Footer;
import bd.edu.seu.wcfrontendnavigation.ui.Header;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.servlet.http.HttpSession;

@Route("admission-officer")
public class AdmissionOfficerView extends VerticalLayout {

    public AdmissionOfficerView(HttpSession httpSession) {
        super();

        Header header = new Header(httpSession);
        header.addAttachListener(event -> {
            LoginToken loginToken = header.getLoginToken();
            if(!loginToken.getRole().equals(Role.ADMISSION_OFFICER)){
                httpSession.removeAttribute("user");
                header.getUI().ifPresent(ui -> ui.navigate("login"));
            }
        });

        Div body = new Div();
        body.add(new Span("Admission Officer"));

        Footer footer = new Footer();

        add(header, body, footer);
    }
}
