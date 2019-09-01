package bd.edu.seu.wcfrontendnavigation.service;

import bd.edu.seu.wcfrontendnavigation.enums.Role;
import bd.edu.seu.wcfrontendnavigation.model.LoginToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void studentAuthTest(){
        LoginToken loginToken = authenticationService.authenticate("1234", "AsifCse");

        assertEquals("1234", loginToken.getUsername());
        assertEquals("AsifCse", loginToken.getPassword());
    }

    @Test
    public void coordinatiorAuthTest(){
        LoginToken loginToken = authenticationService.authenticate("KMH", "KMH");

        System.out.println(loginToken.getUsername());
        System.out.println(loginToken.getPassword());
        System.out.println(loginToken.getRole());
        assertEquals("KMH", loginToken.getUsername());
        assertEquals("KMH", loginToken.getPassword());
    }

    @Test
    public void wrongAuthTest(){
        LoginToken token = authenticationService.authenticate("abcd", "1234");

        System.out.println(token.getUsername());
        System.out.println(token.getPassword());
        System.out.println(token.getRole());
        assertNull(token.getUsername());
        assertNull(token.getPassword());
        assertEquals(Role.NO_ROLE, token.getRole());
    }
}
