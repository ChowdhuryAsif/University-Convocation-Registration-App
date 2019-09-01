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
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route("login")
public class LoginView extends Dialog {

    public LoginView(AuthenticationService authenticationService){
        super();

        Image logo = new Image();
        logo.setSrc("https://seu.edu.bd/images/logo_1.png");

        TextField username = new TextField("StudentID/Initial", "13 digit id or initial");
        PasswordField passwordField = new PasswordField("Password", "your password");

        Button loginButton = new Button("Login");
        Button forgotPassButton = new Button("Forgot Password");
        Label statusBar = new Label();

        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        loginButton.addClickShortcut(Key.ENTER);

        loginButton.addClickListener(event -> {
            LoginToken loginToken = authenticationService.authenticate(username.getValue(), passwordField.getValue());

            switch (loginToken.getRole()){
                case STUDENT:
                    loginButton.getUI().ifPresent(ui -> ui.navigate("student"));
                    break;
                case HR_DEPUTY_REGISTRAR:
                    loginButton.getUI().ifPresent(ui -> ui.navigate("hr-deputy"));
                    break;
                case ADMISSION_OFFICER:
                    loginButton.getUI().ifPresent(ui -> ui.navigate("admission-officer"));
                    break;
                case COORDINATOR:
                    loginButton.getUI().ifPresent(ui -> ui.navigate("login"));
                    break;
                case EXAM_OFFICER:
                    loginButton.getUI().ifPresent(ui -> ui.navigate("login"));
                    break;
                case DEPUTY_REGISTRAR:
                    loginButton.getUI().ifPresent(ui -> ui.navigate("deputy-registrar"));
                    break;
                case NO_ROLE:
                    statusBar.setText("Incorrect Username or Password!");
                    break;
                default:
                    break;
            }
        });

        FormLayout formLayout = new FormLayout();
        formLayout.add(logo, username, passwordField, loginButton, forgotPassButton, statusBar);

        setWidth("300px");
        add(formLayout);
        open();
    }
}
