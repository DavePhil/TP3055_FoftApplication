package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Niveau;
import com.tpinf3055.foft.modele.Salle;
import com.tpinf3055.foft.modele.Semestre;
import com.tpinf3055.foft.modele.Specialite;
import com.tpinf3055.foft.repository.*;
import com.tpinf3055.foft.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
public class SalleController {
    @Autowired
    private SalleService salleService;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private DelegueRepository delegueRepository;
    @Autowired
    private FicheRepository ficheRepository;

    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private SemestreRepository semestreRepository;
    @Autowired
    SpecialiteRepository specialiteRepository;
    @Autowired
    NiveauRepository niveauRepository;

    @PostMapping("/Salle")
    @ResponseBody
    public Salle createSalle(@RequestBody Salle salle){
        return salleService.saveSalle(salle);
    }

    @GetMapping("/Salle/{id}")
    @ResponseBody
    public Salle getSalle(@PathVariable("id") final Integer id ){
        Optional<Salle> salle = salleService.getSalle(id);
        if(salle.isPresent()) {
            return salle.get();
        } else {
            return null;
        }
    }

    @GetMapping("/Salle")
    @ResponseBody
    public Iterable<Salle> getSalles() {
        return salleService.getSalles();
    }

    @DeleteMapping("/Salle/{id}")
    @ResponseBody
    public void deleteSalle(@PathVariable("id") final Integer id) {
        salleService.deleteSalle(id);
    }

    @GetMapping("/ListSalle")
    public String allEnseigant(Model model) //, @PathVariable("id") int id)
    {

        List<Salle> salles = salleRepository.findAll();
        long delCount = delegueRepository.count();
        long ensCount = enseignantRepository.count();
        long ficheCount = ficheRepository.count();
        model.addAttribute("delCount", delCount);
        model.addAttribute("ensCount", ensCount);
        model.addAttribute("sales", salles);
        model.addAttribute("fichecount",ficheCount);
        List<Niveau> niveaux=niveauRepository.findAll();
        List<Specialite> specialites = specialiteRepository.findAll();
        List<Semestre> semestre =semestreRepository.findAll();
        model.addAttribute("semestre", semestre);

        model.addAttribute("specialite", specialites);
        model.addAttribute("niveau", niveaux);


//niveauService.CreateNiveauToDB(code);// PostMapping
        return "salle.html";

    }
    @PostMapping("/Createsalle")
    public String  CreatSalle(Model model,@RequestParam("nom") String nom)//,@PathVariable("id") int id)
    {//model.addAttribute("userid", id);
        salleService.saveSalleToDB(nom);
        return "redirect:/ListSalle";
    }

    @GetMapping("/deleteSalle/{id}")
    public String deleteSalles(@PathVariable("id") int id)
    {

        salleService.deleteSalle(id);
        return "redirect:/ListSalle";
    }
}
