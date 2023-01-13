package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Niveau;
import com.tpinf3055.foft.service.NiveauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class NiveauController {

    @Autowired
    private NiveauService niveauService;

    @PostMapping("/Niveau")
    public Niveau createNiveau(@RequestBody Niveau niveau){
        return niveauService.saveNiveau(niveau);
    }

    @GetMapping("/Niveau/{id}")
    public Niveau getNiveau(@PathVariable("id") final Integer id ){
        Optional<Niveau>  niveau = niveauService.getNiveau(id);
        if(niveau.isPresent()) {
            return niveau.get();
        } else {
            return null;
        }
    }
    @DeleteMapping("/Niveau/{id}")
    public void deleteNiveau(@PathVariable("id") final Integer id) {
        if(niveauService.getNiveau(id).isPresent()){
            deleteNiveau(id);
        }
        else System.out.println("absent");
    }

    @GetMapping("/Niveau")
    public Iterable<Niveau> getNiveaux() {
        return niveauService.getNiveaux();
    }



}
