package com.example.apiTrabalhoUnipac.recurso;
import com.example.apiTrabalhoUnipac.model.domain.Trabalho;
import com.example.apiTrabalhoUnipac.model.repository.TrabalhoRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/Trabalhos")
public class TrabalhoRecurso {
    @Autowired
    private TrabalhoRepositorio trabalhoRepositorio;

    @GetMapping("/{id}")
    public ResponseEntity<Trabalho> getTrabalho(@PathVariable("id") Long id){
        Optional<Trabalho> trabalho = trabalhoRepositorio.findById(id);

        if (!trabalho.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(trabalho.get());
    }

    @GetMapping
    public ResponseEntity<List<Trabalho>> getTrabalhoList(){
        List<Trabalho> trabalhos = trabalhoRepositorio.findAll();
        if (trabalhos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(trabalhos);
    }

    @PostMapping
    public ResponseEntity<Trabalho> addTrabalho(@RequestBody Trabalho trabalho){
        log.info("Gravou o trabalho: " +trabalho.toString());

        Trabalho saved = trabalhoRepositorio.save(trabalho);

        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(url).body(saved);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        try {
            trabalhoRepositorio.deleteById(id);
            return ResponseEntity.ok("Trabalho apagado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Trabalho não pode ser apagado" + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Trabalho> updateTrabalho(@PathVariable("id") Long id, @RequestBody Trabalho novoTrabalho){
        Optional<Trabalho> trabalho = trabalhoRepositorio.findById(id);

        if (trabalho.isPresent()) {
            Trabalho trabalhoUpdate = trabalho.get();
            trabalhoUpdate.setTituloTrabalho(novoTrabalho.getTituloTrabalho());
            trabalhoUpdate.setProfessor(novoTrabalho.getProfessor());
            trabalhoUpdate.setData(novoTrabalho.getData());

            trabalhoRepositorio.save(trabalhoUpdate);

            return ResponseEntity.ok(trabalhoUpdate);
        } else {
            log.info("Trabalho nao pode ser alterado: " + trabalho.toString());
        }

        return ResponseEntity.noContent().build();
    }
}
