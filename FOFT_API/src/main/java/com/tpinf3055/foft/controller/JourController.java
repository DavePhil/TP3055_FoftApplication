package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Jour;
import com.tpinf3055.foft.service.JourService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Data
public class JourController {
    @Autowired
    private JourService jourService;

    @PostMapping("/jour")
    @ResponseBody
    public Jour create(@RequestBody Jour jour){
        return jourService.saveJour(jour);
    }

    @GetMapping("/jour")
    @ResponseBody
    public Optional<Jour> getJour(@PathVariable("id") int id){
        return jourService.findJourById(id);
    }

    @GetMapping("/jours")
    @ResponseBody
    public List<Jour> getJours(){
        return jourService.findJours();
    }
}
