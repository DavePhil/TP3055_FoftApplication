package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Specialite;
import com.tpinf3055.foft.repository.DelegueRepository;
import com.tpinf3055.foft.repository.EnseignantRepository;
import com.tpinf3055.foft.repository.FicheRepository;
import com.tpinf3055.foft.repository.SpecialiteRepository;
import com.tpinf3055.foft.service.SpecialiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
public class SpecialiteController {
    @Autowired
    private SpecialiteService specialiteService;
    @Autowired
    private SpecialiteRepository specialiteRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private DelegueRepository delegueRepository;
    @Autowired
    private FicheRepository ficheRepository;

    @PostMapping("/Specialite")
    @ResponseBody
    public Specialite createSpecialite(@RequestBody Specialite specialite){
        return specialiteService.saveSpecialite(specialite);
    }

    @GetMapping("/Specialite/{id}")
    @ResponseBody
    public Specialite getSpecialite(@PathVariable("id") final Integer id ){
        Optional<Specialite> specialite = specialiteService.getSpecialite(id);
        if(specialite.isPresent()) {
            return specialite.get();
        } else {
            return null;
        }
    }

    @GetMapping("/Specialite")
    @ResponseBody
    public Iterable<Specialite> getSpecialite() {
        return specialiteService.getSpecialites();
    }

    @DeleteMapping("/Specialite/{id}")
    @ResponseBody
    public void deleteSpecialite(@PathVariable("id") final Integer id) {
        specialiteService.deleteSpecialite(id);
    }

    @GetMapping("/ListSpecialite")
    public String AllSpecialite(Model model) //, @PathVariable("id") int id)
    {

        List<Specialite> Specialites = specialiteRepository.findAll();
        long delCount = delegueRepository.count();
        long ensCount = enseignantRepository.count();
        long fichecount = ficheRepository.count();
        //List<Admin> admin = adminRepository.findAll();
        //model.addAttribute("admins", admin);
        model.addAttribute("delCount", delCount);
        model.addAttribute("ensCount", ensCount);
        model.addAttribute("specialites", Specialites);
        model.addAttribute("fichecount",fichecount);


//niveauService.CreateNiveauToDB(code);// PostMapping
        return "Specialite.html";

    }
    @PostMapping("/createspecialite")
    public String  CreatSpecialite(Model model,@RequestParam("code") String code)//,@PathVariable("id") int id)
    {
        //model.addAttribute("userid", id);
        Specialite sp=new Specialite();
        sp.setCode(code);
        specialiteService.saveSpecialite(sp);
        return "redirect:/ListSpecialite";
    }

    @GetMapping("/deletespecialite/{id}")
    public String deleteSpecialite (@PathVariable("id") int id)
    {specialiteService.deleteSpecialite(id);

        return "redirect:/ListSpecialite";
    }

}
