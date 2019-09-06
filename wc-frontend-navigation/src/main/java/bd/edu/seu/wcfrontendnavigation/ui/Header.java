package bd.edu.seu.wcfrontendnavigation.ui;

import bd.edu.seu.wcfrontendnavigation.model.LoginToken;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import javax.servlet.http.HttpSession;

public class Header extends HorizontalLayout {
    private LoginToken loginToken;
    private Label fullNameLabel;

    public Header(HttpSession httpSession) {
        super();

        Image logo = new Image();
        logo.setSrc("https://i.paste.pics/6mnqx.png");
        logo.setWidth("200px");
        logo.setHeight("50px");
        loginToken = (LoginToken) httpSession.getAttribute("user");
        if(loginToken == null)
            loginToken = new LoginToken();

        fullNameLabel = new Label();


        Button logoutButton = new Button("Logout", VaadinIcon.SIGN_OUT.create());
        logoutButton.getStyle().set("margin-left", "auto");
        logoutButton.addClickListener(event -> {
            httpSession.removeAttribute("user");
            logoutButton.getUI().ifPresent(ui -> ui.navigate("login"));
        });

        add(logo, fullNameLabel, logoutButton);
    }

    public LoginToken getLoginToken() {
        return this.loginToken;
    }

    public void setFullNameLabel(String fullName){
        fullNameLabel.setText(fullName);
    }
}
