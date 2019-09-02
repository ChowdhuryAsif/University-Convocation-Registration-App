package bd.edu.seu.wcfrontendnavigation.model;

import bd.edu.seu.wcfrontendnavigation.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(of = {"username"})
public class LoginToken {
    private String username;
    private String password;
    private Role role;

    public LoginToken() {
        this.role = Role.NO_ROLE;
    }
}
