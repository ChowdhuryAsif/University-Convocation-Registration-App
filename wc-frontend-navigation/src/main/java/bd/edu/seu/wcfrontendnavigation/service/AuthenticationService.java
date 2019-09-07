package bd.edu.seu.wcfrontendnavigation.service;

import bd.edu.seu.wcfrontendnavigation.enums.Role;
import bd.edu.seu.wcfrontendnavigation.model.LoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${authUrl}/authorization")
    private String baseUrlAuth;

    public LoginToken authenticate(String username, String password) {
        ResponseEntity<LoginToken> responseEntity = restTemplate.exchange(
                baseUrlAuth + "/" + username,
                HttpMethod.GET,
                null,
                LoginToken.class);

        LoginToken entityBody = responseEntity.getBody();

        String exactPass = entityBody.getPassword();
        if (exactPass == null || !exactPass.equals(password))
            return new LoginToken(null, null, Role.NO_ROLE);
        else
            return new LoginToken(entityBody.getUsername(), entityBody.getPassword(), entityBody.getRole());
    }

}
