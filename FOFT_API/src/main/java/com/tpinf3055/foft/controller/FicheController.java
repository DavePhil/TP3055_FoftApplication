package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Fiche;
import com.tpinf3055.foft.service.FicheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class FicheController {

    @Autowired
    private FicheService ficheService;

    @PostMapping("/Fiche")
    public Fiche createFiche(@RequestBody Fiche fiche){
        return ficheService.saveFiche(fiche);
    }

    @GetMapping("/Fiche/{id}")
    public Fiche getFiche(@PathVariable("id") final Integer id ){
        Optional<Fiche> fiche = ficheService.getFiche(id);
        if(fiche.isPresent()) {
            return fiche.get();
        } else {
            return null;
        }
    }

    @GetMapping("/Fiche")
    public Iterable<Fiche> getFiches() {
        return ficheService.getFiches();
    }

    @DeleteMapping("/Fiche/{id}")
    public void deleteFiche(@PathVariable("id") final Integer id) {
        ficheService.deleteFiche(id);
    }
}
