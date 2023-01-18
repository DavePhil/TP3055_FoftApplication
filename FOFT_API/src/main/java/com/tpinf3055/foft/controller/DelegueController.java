package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Delegue;
import com.tpinf3055.foft.service.DelegueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
public class DelegueController {

    @Autowired
    private DelegueService delegueService;

    @PostMapping("/Delegue")
    public Delegue createDelegue(@RequestBody Delegue delegue){
         return delegueService.saveDelegue(delegue);
    }
    @PostMapping("/createDelegue")
    public Delegue createDelegue(@RequestParam("photo")MultipartFile photo,
                                 @RequestParam("nom")String nom,
                                 @RequestParam("email")String email,
                                 @RequestParam("matricule")String matricule,
                                 @RequestParam("niveau")String niveau){

        return delegueService.saveProductToDB(photo,nom, email, matricule, niveau);
    }


    @GetMapping("/Delegue/{id}")
    public Delegue getDelegue(@PathVariable("id") final Integer id ){
        Optional<Delegue> delegue = delegueService.getDelegue(id);
        if(delegue.isPresent()) {
            return delegue.get();
        } else {
            return null;
        }
    }

    @GetMapping("/Delegue")
    public Iterable<Delegue> getDelegues() {
        return delegueService.getdelegues();
    }

    @DeleteMapping("/Delegue/{id}")
    public void deleteDelegue(@PathVariable("id") final Integer id) {
        delegueService.deleteDelegue(id);
    }
}
