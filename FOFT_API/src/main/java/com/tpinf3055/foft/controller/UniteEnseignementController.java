package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Niveau;
import com.tpinf3055.foft.modele.Semestre;
import com.tpinf3055.foft.modele.Specialite;
import com.tpinf3055.foft.modele.UniteEnseignement;
import com.tpinf3055.foft.repository.*;
import com.tpinf3055.foft.service.UniteEnseignementService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@Controller
public class UniteEnseignementController {
    @Autowired
    private UniteEnseignementService uniteEnseignementService;
    @Autowired
    private UniteEnseignementRepository uniteEnseignementRepository;
    @Autowired
    private DelegueRepository delegueRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private FicheRepository ficheRepository;
    @Autowired
    private NiveauRepository niveauRepository;
    @Autowired
    private SemestreRepository semestreRepository;

    @Autowired
    private SpecialiteRepository specialiteRepository;

    @PostMapping("/UniteEnseignement")
    @ResponseBody
    public UniteEnseignement createUniteEnseignement(@RequestBody UniteEnseignement uniteEnseignement){
        return uniteEnseignementService.saveUniteEnseignement(uniteEnseignement);
    }

    @GetMapping("/UniteEnseignement/{id}")
    @ResponseBody
    public UniteEnseignement getUniteEnseignement(@PathVariable("id") final Integer id ){
        Optional<UniteEnseignement> uniteEnseignement = uniteEnseignementService.getUniteEnseignement(id);
        if(uniteEnseignement.isPresent()) {
            return uniteEnseignement.get();
        } else {
            return null;
        }
    }

    @GetMapping("/UniteEnseignement")
    @ResponseBody
    public Iterable<UniteEnseignement> getUniteEnseignements() {
        return uniteEnseignementService.getUniteEnseignements();
    }


    @DeleteMapping("/UniteEnseignement/{id}")
    @ResponseBody
    public void deleteUniteEnseignement(@PathVariable("id") final Integer id) {
        uniteEnseignementService.deleteUniteEnseignement(id);
    }

    @GetMapping("/getCodeUniteEnseignement")
    @ResponseBody
    public List<UniteEnseignement> getCodeUniteEnseignement(){
        return uniteEnseignementService.getCodeUniteEnseignement();
    }

    @GetMapping("/uniteEnseignements/{id_semestre}/{id_niveau}")
    @ResponseBody
    public Iterable<UniteEnseignement> getUniteEnseignements(@PathVariable("id_semestre") Integer semestre, @PathVariable("id_niveau") Integer niveau) {
        return uniteEnseignementService.getUniteEnseignementsBySemestreAndNiveau(semestre,niveau);
    }

    @GetMapping("/ajoutUe")
    public String addUe()
    {
        // lien vers le temolate   return "/login.html";
        return  "ok";
    }




    /**********---------------------------------------------------------------------------------------********************/
    @GetMapping("/ListUE")
    public String  allUE(Model model ) //@RequestParam("code") String code) // postMapping
    {
        List<UniteEnseignement>  ue=uniteEnseignementRepository.findAll();
        model.addAttribute("Ues", ue);
        long delCount=delegueRepository.count();
        List<Niveau>  niveaux=niveauRepository.findAll();
        model.addAttribute("niveau", niveaux);
        List<Semestre> semestre =semestreRepository.findAll();
        model.addAttribute("semestre", semestre);
        long ensCount=enseignantRepository.count();
        long fichecount = ficheRepository.count();
        List<Specialite> specialites = specialiteRepository.findAll();

        model.addAttribute("specialite", specialites);
        model.addAttribute("niveau", niveaux);

        model.addAttribute("delCount", delCount);
        model.addAttribute("ensCount", ensCount);
        model.addAttribute("fichecount",fichecount);


//niveauService.CreateNiveauToDB(code);// PostMapping
        return "/UE.html";
    }

    @GetMapping("/deleteUE/{id}")
    public String deleteNiveau(@PathVariable("id") int id)
    {
        uniteEnseignementService.deleteUniteEnseignement(id);
        return "redirect:/ListUE";
    }


    @PostMapping("/Createue")
    public String CreateuUE(@RequestParam("code") String code, @RequestParam("niveau") Niveau niveau, @RequestParam("semestre") Semestre semestre, @RequestParam("specialite")Specialite specialite)
    {
        uniteEnseignementService.CreateUEToDB(code,niveau,semestre,specialite);
        return "redirect:/ListUE";

    }



}
