package bd.edu.seu.wcuniprogram.controller;

import bd.edu.seu.wcuniprogram.exception.ResourceAlreadyExistsException;
import bd.edu.seu.wcuniprogram.exception.ResourceDoesNotExistsException;
import bd.edu.seu.wcuniprogram.model.Program;
import bd.edu.seu.wcuniprogram.service.ProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/programs")
public class ProgramController {
    private ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("")
    public ResponseEntity<List<Program>> getProgrames() {
        List<Program> programList = programService.findAll();
        return ResponseEntity.ok(programList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Program> getProgram(@PathVariable String id) {
        try {
            Program program = programService.findById(id);
            return ResponseEntity.ok(program);
        } catch (ResourceDoesNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Program> insertProgram(@RequestBody Program program) {
        try {
            Program insertedProgram = programService.insertProgram(program);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertedProgram);
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProgram(@PathVariable String id) {
        try {
            Boolean deleteByid = programService.deleteByid(id);
            return ResponseEntity.ok(id);
        } catch (ResourceDoesNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Program> updateProgram(@PathVariable String id, @RequestBody Program program) {
        try {
            Program updatedProgram = programService.updateProgram(id, program);
            return ResponseEntity.ok(updatedProgram);
        } catch (ResourceDoesNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
