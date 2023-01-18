package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.UniteEnseignement;
import com.tpinf3055.foft.service.UniteEnseignementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UniteEnseignementController {
    @Autowired
    private UniteEnseignementService uniteEnseignementService;

    @PostMapping("/UniteEnseignement")
    public UniteEnseignement createUniteEnseignement(@RequestBody UniteEnseignement uniteEnseignement){
        return uniteEnseignementService.saveUniteEnseignement(uniteEnseignement);
    }

    @GetMapping("/UniteEnseignement/{id}")
    public UniteEnseignement getUniteEnseignement(@PathVariable("id") final Integer id ){
        Optional<UniteEnseignement> uniteEnseignement = uniteEnseignementService.getUniteEnseignement(id);
        if(uniteEnseignement.isPresent()) {
            return uniteEnseignement.get();
        } else {
            return null;
        }
    }

    @GetMapping("/UniteEnseignement")
    public Iterable<UniteEnseignement> getUniteEnseignements() {
        return uniteEnseignementService.getUniteEnseignements();
    }

    @DeleteMapping("/UniteEnseignement/{id}")
    public void deleteUniteEnseignement(@PathVariable("id") final Integer id) {
        uniteEnseignementService.deleteUniteEnseignement(id);
    }

    @GetMapping("/getCodeUniteEnseignement")
    public List<UniteEnseignement> getCodeUniteEnseignement(){
        return uniteEnseignementService.getCodeUniteEnseignement();
    }



}
