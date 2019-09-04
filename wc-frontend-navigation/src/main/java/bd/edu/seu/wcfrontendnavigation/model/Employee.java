package bd.edu.seu.wcfrontendnavigation.model;

import bd.edu.seu.wcfrontendnavigation.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"initial"})
public class Employee {
    private String initial;
    private String name;
    private String loginPass;
    private Role role;
}
