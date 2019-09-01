package bd.edu.seu.wcauthorization.controller;

import bd.edu.seu.wcauthorization.model.LoginToken;
import bd.edu.seu.wcauthorization.service.AuthorizationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"api/v1/authorization"})
public class AuthorizationController {

    private AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("")
    public LoginToken insert(@RequestBody LoginToken loginToken){
        return authorizationService.insert(loginToken);
    }

    @GetMapping("")
    public List<LoginToken> findAll(){
        List<LoginToken> loginTokenList = authorizationService.findAll();
        return loginTokenList;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id){
        authorizationService.deleteById(id);
    }
}
