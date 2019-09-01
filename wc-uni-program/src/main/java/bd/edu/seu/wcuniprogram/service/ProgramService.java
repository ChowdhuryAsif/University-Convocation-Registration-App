package bd.edu.seu.wcuniprogram.service;

import bd.edu.seu.wcuniprogram.exception.ResourceAlreadyExistsException;
import bd.edu.seu.wcuniprogram.exception.ResourceDoesNotExistsException;
import bd.edu.seu.wcuniprogram.model.Program;
import bd.edu.seu.wcuniprogram.repository.ProgramRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramService {
    private ProgramRepository programRepository;

    public ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    public Program findById(String id) throws ResourceDoesNotExistsException {
        Optional<Program> program = programRepository.findById(id);
        if (program.isPresent()) {
            return program.get();
        } else
            throw new ResourceDoesNotExistsException(id + "");
    }

    public List<Program> findAll() {
        return programRepository.findAll();
    }

    public Boolean deleteByid(String id) throws ResourceDoesNotExistsException {
        Optional<Program> program = programRepository.findById(id);
        program.ifPresent(program1 -> programRepository.deleteById(id));
        program.orElseThrow(() -> new ResourceDoesNotExistsException(id + ""));
        return true;
    }

    public Program insertProgram(Program program) throws ResourceAlreadyExistsException {
        Optional<Program> optionalProgram = programRepository.findById(program.getTitle());
        if (optionalProgram.isPresent()) {
            throw new ResourceAlreadyExistsException(program.getTitle() + "");
        } else {
            return programRepository.save(program);
        }
    }

    public Program updateProgram(String id, Program program) throws ResourceDoesNotExistsException {
        Optional<Program> optionalProgram = programRepository.findById(id);
        if (optionalProgram.isPresent()) {
            program.setTitle(id);
            return programRepository.save(program);
        } else {
            throw new ResourceDoesNotExistsException(id + "");
        }
    }
}
