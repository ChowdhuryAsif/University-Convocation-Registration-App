package bd.edu.seu.employee.model;

import bd.edu.seu.employee.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"username"})
public class LoginToken {
    private String username;
    private String password;
    private Role role;
}
