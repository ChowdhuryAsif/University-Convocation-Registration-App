package bd.edu.seu.wcfrontendnavigation.model;

import bd.edu.seu.wcfrontendnavigation.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinator{
    private String initial;
    private String name;
    private String loginPass;
    private Role role;
}
