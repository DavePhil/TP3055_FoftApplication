package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.*;
import com.tpinf3055.foft.repository.*;
import com.tpinf3055.foft.service.DelegueService;
import com.tpinf3055.foft.service.ProgrammeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ProgrammeController {

    @Autowired
    private ProgrammeService programmeService;
    @Autowired
    private DelegueService delegueService;
    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private UniteEnseignementRepository uniteEnseignementRepository;

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private  JourRepository jourRepository;
    @Autowired
    private DelegueRepository delegueRepository;
    @Autowired
    private NiveauRepository niveauRepository;
    @Autowired
    private FicheRepository ficheRepository;

    @GetMapping("/ajoutProgramme")
    public String tocreateProgramme(Model model){

        return "ajoutProgramme.html";
    }

    @PostMapping("/programme")
    public String createProgramme(@RequestParam("heureDeDebut") @DateTimeFormat(pattern = "HH:mm")LocalTime heureDeDebut,
                                     @RequestParam("heureDeFin") @DateTimeFormat(pattern = "HH:mm") LocalTime heureDeFin,
                                     @RequestParam("totalHoraire") String totalHoraire,
                                     @RequestParam("enseignant") Enseignant enseignant,
                                     @RequestParam("ue") UniteEnseignement uniteEnseignement,
                                     @RequestParam("salle") Salle salle,
                                     @RequestParam("seance") Seance seance,
                                     @RequestParam("jour") Jour jour){
        Programme programme = new Programme();
        programme.setJour(jour);
        programme.setEnseignant(enseignant);
        programme.setSalle(salle);
        programme.setHeureDeDebut(heureDeDebut);
        programme.setTotalHoraire(totalHoraire);
        programme.setHeureDeFin(heureDeFin);
        programme.setUe(uniteEnseignement);
        programme.setSeance(seance);
        programmeService.saveProgramme(programme);
        return "redirect:/HomePage";
    }

    @GetMapping("/programme/{id}")
    @ResponseBody
    public Optional<Programme> getProgrammes(@PathVariable int id){
        return programmeService.getProgramme(id);
    }

    @GetMapping("/programmes")
    @ResponseBody
    public Iterable<Programme> getProgrammes(){
        return programmeService.getProgrammes();
    }

    @GetMapping("/deleteprogramme/{id}")
    public String deleteProgramme(@PathVariable int id){
         programmeService.deleteProgramme(id);
         return "redirect:/HomePage";
    }

    @GetMapping("/jourP")
    @ResponseBody
    public List<Programme> verify(){

        return programmeService.findByJour(programmeService.idOfJour(programmeService.getJour()));
    }

    @GetMapping("/modif/{id}")
    public String modif(Model model, @PathVariable("id") int id){
        Programme programme = programmeService.getprogramme(id);
        model.addAttribute("programme",programme);
        List<Enseignant> enseignants = enseignantRepository.findAll();
        List<UniteEnseignement> ue = uniteEnseignementRepository.findAll();
        List<Salle> salles = salleRepository.findAll();
        List<Seance> seances = seanceRepository.findAll();
        List<Jour> jour = jourRepository.findAll();

        model.addAttribute("enseignant", enseignants);
        model.addAttribute("ue", ue);
        model.addAttribute("salle", salles);
        model.addAttribute("seance",seances);
        model.addAttribute("jour",jour);
        return "modifierProgramme.html";
    }

    @PostMapping("/programmec/{id}")
    public  String updateProgramme(@PathVariable("id") int id, Model model,
                                   @RequestParam(value = "heureDeDebut", required = false) @DateTimeFormat(pattern = "HH:mm")LocalTime heureDeDebut,
                                   @RequestParam(value = "heureDeFin", required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime heureDeFin,
                                   @RequestParam(value = "totalHoraire", required = false) String totalHoraire,
                                   @RequestParam(value = "enseignant", required = false) Enseignant enseignant,
                                   @RequestParam(value = "ue", required = false) UniteEnseignement uniteEnseignement,
                                   @RequestParam(value = "salle", required = false) Salle salle,
                                   @RequestParam(value = "seance", required = false) Seance seance,
                                   @RequestParam(value = "jour", required = false) Jour jour) {

        Programme current = programmeService.updateProgramme(id,heureDeDebut,heureDeFin,totalHoraire,enseignant,uniteEnseignement,salle,seance,jour);
        model.addAttribute("programme",current);

                return "redirect:/HomePage";
    }

    @PostMapping("/ListProgramme")
    public String  allProgramme(Model model, @RequestParam("niveau") int idNiveau, @RequestParam("specialite") int idSpecialite, @RequestParam("semestre") int semestreId )
    {


        List<Niveau> niveaux=niveauRepository.findAll();
        long delCount=delegueRepository.count();
        long ensCount=enseignantRepository.count();
        long ficheCount = ficheRepository.count();
        model.addAttribute("niveau", niveaux);
        model.addAttribute("delCount", delCount);
        model.addAttribute("ensCount", ensCount);
        model.addAttribute("ficheCount",ficheCount);
        List<Programme> programmeLun = programmeService.findBySpecialiteAndNiveau(idSpecialite,idNiveau,1, semestreId);
        List<Programme> programmeMar = programmeService.findBySpecialiteAndNiveau(idSpecialite,idNiveau,2, semestreId);
        List<Programme> programmeMer = programmeService.findBySpecialiteAndNiveau(idSpecialite,idNiveau,3, semestreId);
        List<Programme> programmeJeu = programmeService.findBySpecialiteAndNiveau(idSpecialite,idNiveau,4, semestreId);
        List<Programme> programmeVen = programmeService.findBySpecialiteAndNiveau(idSpecialite,idNiveau,5, semestreId);
        List<Programme> programmeSam = programmeService.findBySpecialiteAndNiveau(idSpecialite,idNiveau,6, semestreId);
        List<Programme> programmeDim = programmeService.findBySpecialiteAndNiveau(idSpecialite,idNiveau,7, semestreId);
        model.addAttribute("programmeLun",programmeLun);
        model.addAttribute("programmeMar",programmeMar);
        model.addAttribute("programmeMer",programmeMer);
        model.addAttribute("programmeJeu",programmeJeu);
        model.addAttribute("programmeVen",programmeVen);
        model.addAttribute("programmeSam",programmeSam);
        model.addAttribute("programmeDim",programmeDim);
        List<Enseignant> enseignants = enseignantRepository.findAll();
        List<UniteEnseignement> ue = uniteEnseignementRepository.findAll();
        List<Salle> salles = salleRepository.findAll();
        List<Seance> seances = seanceRepository.findAll();
        List<Jour> jour = jourRepository.findAll();

        model.addAttribute("enseignant", enseignants);
        model.addAttribute("ue", ue);
        model.addAttribute("salle", salles);
        model.addAttribute("seance",seances);
        model.addAttribute("jour",jour);



//niveauService.CreateNiveauToDB(code);// PostMapping
        return "timeTable.html";
    }

    @GetMapping("/listprogram/{id}")
    public String programme (Model model, @PathVariable("id") Integer id){

        Programme programme = programmeService.getprogramme(id);
        model.addAttribute("programme",programme);

        return "soustimetable.html";
    }

}
