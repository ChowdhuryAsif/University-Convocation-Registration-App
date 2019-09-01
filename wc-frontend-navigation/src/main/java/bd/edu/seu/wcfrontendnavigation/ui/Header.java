package bd.edu.seu.wcfrontendnavigation.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class Header extends HorizontalLayout {

    public Header() {
        super();

        Label fullNameLabel = new Label();
        Button logoutButton = new Button("logout");

        logoutButton.addClickListener(event -> logoutButton.getUI().ifPresent(ui -> ui.navigate("login")));

        add(fullNameLabel, logoutButton);
    }
}
