package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Niveau;
import com.tpinf3055.foft.repository.DelegueRepository;
import com.tpinf3055.foft.repository.EnseignantRepository;
import com.tpinf3055.foft.repository.FicheRepository;
import com.tpinf3055.foft.repository.NiveauRepository;
import com.tpinf3055.foft.service.NiveauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
public class NiveauController {
    @Autowired
    private DelegueRepository delegueRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private NiveauRepository niveauRepository;

    @Autowired
    private FicheRepository ficheRepository;

    @Autowired
    private NiveauService niveauService;

    @PostMapping("/niveau")
    @ResponseBody
    public Niveau createNiveau(@RequestBody Niveau niveau){
        return niveauService.saveNiveau(niveau);
    }

    @GetMapping("/niveau/{id}")
    @ResponseBody
    public Niveau getNiveau(@PathVariable("id") final Integer id ){
        Optional<Niveau>  niveau = niveauService.getNiveau(id);
        if(niveau.isPresent()) {
            return niveau.get();
        } else {
            return null;
        }
    }
    @DeleteMapping("/niveau/{id}")
    @ResponseBody
    public void deleteNiveau(@PathVariable("id") final Integer id) {
        if(niveauService.getNiveau(id).isPresent()){
            deleteNiveau(id);
        }
        else System.out.println("absent");
    }

    @GetMapping("/niveaux")
    @ResponseBody
    public Iterable<Niveau> getNiveaux() {
        return niveauService.getNiveaux();
    }

    @GetMapping("/ListNiveau")
    public String  allNiveau(Model model ) //@RequestParam("code") String code) // postMapping
    {
        List<Niveau> niveaux =niveauRepository.findAll();
        model.addAttribute("niveaux", niveaux);
        long delCount=delegueRepository.count();
        long fichecount = ficheRepository.count();
        long ensCount=enseignantRepository.count();

        model.addAttribute("delCount", delCount);
        model.addAttribute("ensCount", ensCount);
        model.addAttribute("fichecount",fichecount);


//niveauService.CreateNiveauToDB(code);// PostMapping
        return "/niveau.html";
    }



    @GetMapping("/deleteniveau/{id}")
    public String deleteNiveau(@PathVariable("id") int id)
    {

        niveauService.deleteNiveau(id);
        return "redirect:/ListNiveau";
    }


    @PostMapping("/createniveau")
    public String creatNiveau(@RequestParam("code") String code)
    {
        niveauService.CreateNiveauToDB(code);
        return "redirect:/ListNiveau";

    }



}
