package bd.edu.seu.employee.model;

import bd.edu.seu.employee.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"initial"})
public class Employee {
    @Id
    private String initial;
    private String name;
    private String loginPass;
    private Role role;
}
