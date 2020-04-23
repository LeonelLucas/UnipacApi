package com.example.apiTrabalhoUnipac.recurso;
import com.example.apiTrabalhoUnipac.model.domain.Professor;
import com.example.apiTrabalhoUnipac.model.repository.ProfessorRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("/Professor")
public class ProfessorRecurso {
    @Autowired
    private ProfessorRepositorio professorRepositorio;

    @GetMapping("/{id}")
    public ResponseEntity<Professor> getProfessor(@PathVariable("id") Long id){
        Optional<Professor> professor = professorRepositorio.findById(id);

        if (!professor.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(professor.get());
    }

    @GetMapping
    public ResponseEntity<List<Professor>> getProfessorList(){
        List<Professor> professor = professorRepositorio.findAll();
        if (professor.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(professor);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Professor> updateProfessor(@PathVariable("id") Long id, @RequestBody Professor novoProfessor){
        Optional<Professor> professor = professorRepositorio.findById(id);

        if (professor.isPresent()) {
            Professor professorUpdate = professor.get();
            professorUpdate.setNome(novoProfessor.getNome());

            professorRepositorio.save(professorUpdate);

            return ResponseEntity.ok(professorUpdate);
        } else {
            log.info("Professor nao pode ser alterado: " + professor.toString());
        }

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        try {
            professorRepositorio.deleteById(id);
            return ResponseEntity.ok("Deletado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Não pode ser deletado" + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Professor> addProfessor(@RequestBody Professor professor){
        log.info("Gravou o professor: " +professor.toString());

        Professor saved = professorRepositorio.save(professor);

        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(url).body(saved);
    }

}
