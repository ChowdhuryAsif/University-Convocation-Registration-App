package bd.edu.seu.wcauthorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class WcAuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(WcAuthorizationApplication.class, args);
    }

}
