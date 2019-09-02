package bd.edu.seu.wcfrontendnavigation.ui.student;

import bd.edu.seu.wcfrontendnavigation.enums.Role;
import bd.edu.seu.wcfrontendnavigation.model.LoginToken;
import bd.edu.seu.wcfrontendnavigation.ui.Footer;
import bd.edu.seu.wcfrontendnavigation.ui.Header;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

import javax.servlet.http.HttpSession;

@Route("student")
public class StudentView extends VerticalLayout {

    public StudentView(HttpSession httpSession) {
        super();

        Header header = new Header(httpSession);
        header.addAttachListener(event -> {
            LoginToken loginToken = header.getLoginToken();
            if(!loginToken.getRole().equals(Role.STUDENT)){
                httpSession.removeAttribute("user");
                header.getUI().ifPresent(ui -> ui.navigate("login"));
            }
        });



        Container container = new Container();
        container.setHeight("450px");
        Footer footer = new Footer();


        add(header, container, footer);
    }
}
