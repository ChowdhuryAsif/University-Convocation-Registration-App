package bd.edu.seu.wcfrontendnavigation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WcFrontendNavigationApplicationTests {

    @Bean
    private RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Test
    public void contextLoads() {
    }

}
