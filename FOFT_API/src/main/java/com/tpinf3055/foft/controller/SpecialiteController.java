package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Specialite;
import com.tpinf3055.foft.service.SpecialiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class SpecialiteController {
    @Autowired
    private SpecialiteService specialiteService;

    @PostMapping("/Specialite")
    public Specialite createSpecialite(@RequestBody Specialite specialite){
        return specialiteService.saveSpecialite(specialite);
    }

    @GetMapping("/Specialite/{id}")
    public Specialite getSpecialite(@PathVariable("id") final Integer id ){
        Optional<Specialite> specialite = specialiteService.getSpecialite(id);
        if(specialite.isPresent()) {
            return specialite.get();
        } else {
            return null;
        }
    }

    @GetMapping("/Specialite")
    public Iterable<Specialite> getSpecialite() {
        return specialiteService.getSpecialites();
    }

    @DeleteMapping("/Specialite/{id}")
    public void deleteSpecialite(@PathVariable("id") final Integer id) {
        specialiteService.deleteSpecialite(id);
    }
}
