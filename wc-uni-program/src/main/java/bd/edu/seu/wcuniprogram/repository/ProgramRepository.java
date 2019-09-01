package bd.edu.seu.wcuniprogram.repository;

import bd.edu.seu.wcuniprogram.model.Program;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends MongoRepository<Program, String> {
}
