package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Fiche;
import com.tpinf3055.foft.modele.Semestre;
import com.tpinf3055.foft.service.SemestreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class SemestreController {
    @Autowired
    private SemestreService semestreService;

    @PostMapping("/semestre")
    @ResponseBody
    public Semestre createSemestre(@RequestBody Semestre semestre ){
        return semestreService.saveSemestre(semestre);
    }

    @GetMapping("/semestre/{id}")
    @ResponseBody
    public Semestre getSemestre(@PathVariable("id") final Integer id ){
        Optional<Semestre> semestre = semestreService.getSemestre(id);
        if(semestre.isPresent()) {
            return semestre.get();
        } else {
            return null;
        }
    }

    @GetMapping("/semestres")
    @ResponseBody
    public Iterable<Semestre> getSemestre() {
        return semestreService.getSemestres();
    }
}
