package bd.edu.seu.wcauthorization.model;

import bd.edu.seu.wcauthorization.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"username"})
public class LoginToken {
    @Id
    private String username;
    private String password;
    private Role role;
}
