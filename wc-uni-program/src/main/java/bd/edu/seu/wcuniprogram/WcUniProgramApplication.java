package bd.edu.seu.wcuniprogram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class WcUniProgramApplication {

    public static void main(String[] args) {
        SpringApplication.run(WcUniProgramApplication.class, args);
    }

}
