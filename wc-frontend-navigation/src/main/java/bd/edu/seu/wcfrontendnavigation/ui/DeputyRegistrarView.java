package bd.edu.seu.wcfrontendnavigation.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("deputy-registrar")
public class DeputyRegistrarView extends VerticalLayout {

    public DeputyRegistrarView() {
        super();

        Header header = new Header();

        Div body = new Div();
        body.add(new Span("Deputy Registrar"));

        Footer footer = new Footer();

        add(header, body, footer);
    }
}
