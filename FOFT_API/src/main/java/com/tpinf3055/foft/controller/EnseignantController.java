package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Delegue;
import com.tpinf3055.foft.modele.Enseignant;
import com.tpinf3055.foft.service.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
public class EnseignantController {

    @Autowired
    private EnseignantService enseignantService;

    @PostMapping("/Enseignant")
    public Enseignant createEnseignant(@RequestBody Enseignant enseignant){
        return enseignantService.saveEnseignant(enseignant);
    }

    @PostMapping("/createEnseignant")
    public Enseignant createEnseignant(@RequestParam("photo") MultipartFile photo,
                                 @RequestParam("nom")String nom,
                                 @RequestParam("email")String email){

        return enseignantService.saveEnseignantToDB(photo,nom, email);
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

    @PutMapping("/Enseignant/{id}")
    public Enseignant updateEmployee(@PathVariable("id") final int id, @RequestBody Enseignant enseignant) {
        Optional<Enseignant> e = enseignantService.getEnseignant(id);
        if(e.isPresent()) {
            Enseignant current = e.get();

            String nom = enseignant.getNom();
            if(nom != null) {
                current.setNom(nom);
            }
            String mail = enseignant.getEmail();
            if(mail != null) {
                current.setEmail(mail);
            }
            String password = enseignant.getPassword();
            if(password != null) {
                current.setPassword(password);;
            }
            enseignantService.saveEnseignant(current);
            return current;
        } else {
            return null;
        }
    }
}
