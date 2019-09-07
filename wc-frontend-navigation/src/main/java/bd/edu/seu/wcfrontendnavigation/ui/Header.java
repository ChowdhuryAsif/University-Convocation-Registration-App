package bd.edu.seu.wcfrontendnavigation.ui;

import bd.edu.seu.wcfrontendnavigation.model.LoginToken;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import javax.servlet.http.HttpSession;

public class Header extends HorizontalLayout {
    private LoginToken loginToken;
    private Label fullNameLabel;

    public Header(HttpSession httpSession) {
        super();

        Image logo = new Image();
        logo.setSrc("https://i.imgur.com/ZQz7KpX.png");
        logo.setWidth("200px");
        logo.setHeight("50px");
        loginToken = (LoginToken) httpSession.getAttribute("user");
        if (loginToken == null)
            loginToken = new LoginToken();

        fullNameLabel = new Label();


        Button logoutButton = new Button("Logout", VaadinIcon.SIGN_OUT.create());
        logoutButton.getStyle().set("margin-left", "auto");
        logoutButton.addClickListener(event -> {
            httpSession.removeAttribute("user");
            logoutButton.getUI().ifPresent(ui -> ui.navigate("login"));
        });

        HorizontalLayout headerPart = new HorizontalLayout();
        headerPart.add(fullNameLabel, logoutButton);
        fullNameLabel.getStyle()
                .set("margin-top", "5px")
                .set("margin-right", "10px")
                .set("font-size", "20px");
        headerPart.getStyle()
                .set("margin-right", "60px")
                .set("right", "0")
                .set("position", "absolute");

        add(logo, headerPart);
    }

    public LoginToken getLoginToken() {
        return this.loginToken;
    }

    public void setFullNameLabel(String fullName) {
        fullNameLabel.setText(fullName);
    }
}
