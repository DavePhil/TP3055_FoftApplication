package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Enseignant;
import com.tpinf3055.foft.service.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class EnseignantController {

    @Autowired
    private EnseignantService enseignantService;

    @PostMapping("/Enseignant")
    public Enseignant createEnseignant(@RequestBody Enseignant enseignant){
        return enseignantService.saveEnseignant(enseignant);
    }

    @GetMapping("/Enseignant/{id}")
    public Enseignant getEnseignant(@PathVariable("id") final Integer id ){
        Optional<Enseignant> enseignant = enseignantService.getEnseignant(id);
        if(enseignant.isPresent()) {
            return enseignant.get();
        } else {
            return null;
        }
    }

    @GetMapping("/Enseignant")
    public Iterable<Enseignant> getEnseignants() {
        return enseignantService.getEnseignants();
    }

    @DeleteMapping("/Enseignant/{id}")
    public void deleteEnseignant(@PathVariable("id") final Integer id) {
        enseignantService.deleteEnseignant(id);
    }
}
