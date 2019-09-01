package bd.edu.seu.wcauthorization.service;

import bd.edu.seu.wcauthorization.enums.Role;
import bd.edu.seu.wcauthorization.model.LoginToken;
import bd.edu.seu.wcauthorization.repository.AuthorizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorizationService {

    private AuthorizationRepository authorizationRepository;

    public AuthorizationService(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }

    public LoginToken insert(LoginToken loginToken){
        LoginToken token = authorizationRepository.insert(loginToken);
        return token;
    }

    public List<LoginToken> findAll(){
        List<LoginToken> loginTokenList = authorizationRepository.findAll();
        return loginTokenList;
    }

    public LoginToken findById(String id){
        Optional<LoginToken> optionalLoginToken = authorizationRepository.findById(id);
        if(optionalLoginToken.isPresent())
            return optionalLoginToken.get();
        else
            return new LoginToken(null, null, Role.NO_ROLE);
    }

    public void deleteById(String id){
        authorizationRepository.deleteById(id);
    }

    public LoginToken update(String id, LoginToken loginToken){
        Optional<LoginToken> optionalLoginToken = authorizationRepository.findById(id);

        loginToken.setUsername(id);
        return authorizationRepository.save(loginToken);
    }
}
