package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Salle;
import com.tpinf3055.foft.modele.Seance;
import com.tpinf3055.foft.service.SalleService;
import com.tpinf3055.foft.service.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
public class SeanceController {
    @Autowired
    private SeanceService seanceService;

    @PostMapping("/seance")
    @ResponseBody
    public Seance createSeance(@RequestBody Seance salle){
        return seanceService.saveSeance(salle);
    }

    @GetMapping("/seance/{id}")
    @ResponseBody
    public Seance getSalle(@PathVariable("id") final Integer id ){
        Optional<Seance> seance = seanceService.getSeance(id);
        if(seance.isPresent()) {
            return seance.get();
        } else {
            return null;
        }
    }

    @GetMapping("/seance")
    @ResponseBody
    public Iterable<Seance> getSalles() {
        return seanceService.getSeances();
    }

    @DeleteMapping("/seance/{id}")
    @ResponseBody
    public void deleteSalle(@PathVariable("id") final Integer id) {
        seanceService.deleteSeance(id);
    }
}
