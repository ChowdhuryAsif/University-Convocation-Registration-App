package bd.edu.seu.wcfrontendnavigation.ui;

import bd.edu.seu.wcfrontendnavigation.model.LoginToken;
import bd.edu.seu.wcfrontendnavigation.service.AuthenticationService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import javax.servlet.http.HttpSession;


@Route("login")
public class LoginView extends Dialog {

    public LoginView(AuthenticationService authenticationService, HttpSession httpSession){
        super();

        Image logo = new Image();
        logo.setSrc("https://i.paste.pics/6mnqx.png");

        TextField usernameField = new TextField("StudentID/Initial", "13 digit id or initial");
        PasswordField passwordField = new PasswordField("Password", "your password");

        usernameField.setRequired(true);
        passwordField.setRequired(true);

        Button loginButton = new Button("Login");
        Button forgotPassButton = new Button("Forgot Password");
        Label statusBar = new Label();

        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        loginButton.addClickShortcut(Key.ENTER);

        loginButton.addClickListener(event -> {
            LoginToken loginToken = authenticationService.authenticate(usernameField.getValue(), passwordField.getValue());

            switch (loginToken.getRole()){
                case STUDENT:
                    httpSession.setAttribute("user", loginToken);
                    loginButton.getUI().ifPresent(ui -> ui.navigate("student"));
                    break;
                case HR_DEPUTY_REGISTRAR:
                    httpSession.setAttribute("user", loginToken);
                    loginButton.getUI().ifPresent(ui -> ui.navigate("hr-deputy"));
                    break;
                case ADMISSION_OFFICER:
                    httpSession.setAttribute("user", loginToken);
                    loginButton.getUI().ifPresent(ui -> ui.navigate("admission-officer"));
                    break;
                case COORDINATOR:
                    httpSession.setAttribute("user", loginToken);
                    loginButton.getUI().ifPresent(ui -> ui.navigate("login"));
                    break;
                case EXAM_OFFICER:
                    httpSession.setAttribute("user", loginToken);
                    loginButton.getUI().ifPresent(ui -> ui.navigate("exam-officer"));
                    break;
                case DEPUTY_REGISTRAR:
                    httpSession.setAttribute("user", loginToken);
                    loginButton.getUI().ifPresent(ui -> ui.navigate("deputy-registrar"));
                    break;
                case NO_ROLE:
                    //loginButton.getUI().ifPresent(ui -> ui.navigate("login"));
                    Notification.show("Incorrect Username or Password!").addThemeVariants(NotificationVariant.LUMO_ERROR);
                    break;
                default:
                    break;
            }
        });

        usernameField.addFocusListener(event -> usernameField.setValue(""));
        passwordField.addFocusListener(event -> passwordField.setValue(""));

        FormLayout formLayout = new FormLayout();
        formLayout.add(logo, usernameField, passwordField, loginButton, forgotPassButton, statusBar);

        setWidth("300px");
        add(formLayout);
        open();
        setCloseOnOutsideClick(false);
    }
}
