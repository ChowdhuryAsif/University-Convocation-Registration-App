package bd.edu.seu.wcfrontendnavigation;

import bd.edu.seu.wcfrontendnavigation.service.StudentService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Route;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WcFrontendNavigationApplication {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(WcFrontendNavigationApplication.class, args);
    }

}
