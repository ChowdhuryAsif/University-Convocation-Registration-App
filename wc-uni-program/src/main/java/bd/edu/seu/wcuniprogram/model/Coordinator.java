package bd.edu.seu.wcuniprogram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinator {
    @Id
    private String initial;
    private String name;
}
