package bd.edu.seu.wcunidiscoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class WcUniDiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WcUniDiscoveryServerApplication.class, args);
    }

}
