package bd.edu.seu.wcauthorization.repository;

import bd.edu.seu.wcauthorization.model.LoginToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends MongoRepository<LoginToken, String> {
}
