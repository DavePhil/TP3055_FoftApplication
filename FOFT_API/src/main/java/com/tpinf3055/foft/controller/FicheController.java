package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.*;
import com.tpinf3055.foft.repository.*;
import com.tpinf3055.foft.service.DelegueService;
import com.tpinf3055.foft.service.FicheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Controller
public class FicheController {

    @Autowired
    private FicheService ficheService;
    @Autowired
    private DelegueService delegueService;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private DelegueRepository delegueRepository;
    @Autowired
    private NiveauRepository niveauRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private SpecialiteRepository specialiteRepository;
    @Autowired
    private UniteEnseignementRepository uniteEnseignementRepository;
    @Autowired
    private FicheRepository ficheRepository;


    @PostMapping("/Fiche")
    public Fiche createFiche(@RequestBody Fiche fiche){
        return ficheService.saveFiche(fiche);
    }



    @PostMapping("/createFiche")
    @ResponseBody
    public Map<String, String> createFiche (@RequestParam("signatureDelegue") MultipartFile signature,
                                            @RequestParam("semestre")String semestre,
                                            @RequestParam("date") String date,
                                            @RequestParam("titre")String titre,
                                            @RequestParam("heureDebut")String heureDebut,
                                            @RequestParam("heureFin")String heureFin,
                                            @RequestParam("contenu") String contenu,
                                            @RequestParam("totalHoraire") String totalHoraire,
                                            @RequestParam("enseignant") Enseignant enseignant,
                                            @RequestParam("uniteEnseignement")UniteEnseignement uniteEnseignement,
                                            @RequestParam("niveau")Niveau niveau,
                                            @RequestParam("salle") Salle salle,
                                            @RequestParam("specialite") Specialite specialite,
                                            @RequestParam("delegue") Delegue delegue,
                                            @RequestParam("seance") Seance seance
                              ){

        try {
            System.out.println(signature.getContentType());
            if(!signature.getContentType().equals("image/jpeg") && !signature.getContentType().equals("image/png")){
                return Collections.singletonMap("response", "Seuls les fichiers JPEG et PNG sont acceptés");
            }
            ficheService.saveFicheToDB(signature,semestre,titre,heureDebut,heureFin,contenu,totalHoraire,enseignant,uniteEnseignement,niveau,salle, date,specialite,delegue,seance);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.singletonMap("response", "error");
        }
        String returnValue = "Fiche ajoutee avec succes" ;
        return Collections.singletonMap("response", returnValue);
    }

    @GetMapping("/Fiche/{id}")
    @ResponseBody
    public Fiche getFiche(@PathVariable("id") final Integer id ){
        Optional<Fiche> fiche = ficheService.getFiche(id);
        if(fiche.isPresent()) {
            return fiche.get();
        } else {
            return null;
        }
    }

    @GetMapping("/Fiche/{id}/{state}")
    @ResponseBody
    public Fiche getByIdAndState(@PathVariable int id, @PathVariable int state){
        Optional<Fiche> fiche = ficheService.getFicheByIdAndState(id,state);
        if(fiche.isPresent()) {
            return fiche.get();
        } else {
            return null;
        }
    }

    @GetMapping("/Fiche")
    @ResponseBody
    public Iterable<Fiche> getFiches() {
        return ficheService.getFiches();
    }

    @DeleteMapping("/Fiche/{id}")
    @ResponseBody
    public void deleteFiche(@PathVariable("id") final Integer id) {
        ficheService.deleteFiche(id);
    }

    @DeleteMapping("/Fiche/{id}/{state}")
    @ResponseBody
    public void deleteIfReject(@PathVariable int id, @PathVariable int state){
        if(state==2) ficheService.deleteFicheByIdAndState(id, state);
        else return;
    }



    @PutMapping("/valideOrReject/{state}/{id}")
    @ResponseBody
    public int acceptFiche(@PathVariable int state, @PathVariable Integer id) {
       return ficheService.valideOrRejectFiche(state, id);
    }

    @PutMapping("/updateFicheMotif/{id}")
    @ResponseBody
    public int updateFicheMotif(@RequestParam String motif, @PathVariable("id") Integer id ){
        Optional<Fiche> f = ficheService.getFiche(id);
        if(f.isPresent()){
            if (f.get().getState()==0){
                f.get().setState(2);
                ficheService.saveFiche(f.get());
                return ficheService.ajouteMotif(motif, id);
            }
        }
        return 0;
    }




    @PutMapping("/addSignatureEnseignant/{id}")
    @ResponseBody
    public int addSignatureEnseignant (@RequestParam("signature") MultipartFile signature,
                                       @PathVariable("id")Integer id) throws IOException {
        Optional<Fiche> f = ficheService.getFiche(id);
        final String folder = new ClassPathResource("static/SignatureE/").getFile().getAbsolutePath();
        final String route = ServletUriComponentsBuilder.fromCurrentContextPath().path("/SignatureE/").path(signature.getOriginalFilename()).toUriString();
        byte [] bytes = signature.getBytes();
        Path path = Paths.get(folder + File.separator + signature.getOriginalFilename());

        if(f.isPresent()){
            if(f.get().getState() == 0) {
                System.out.println(route);
                Files.write(path,bytes);
                f.get().setState(1);
                f.get().setSignatureEnseignant("/SignatureE/"+signature.getOriginalFilename());
                ficheService.saveFiche(f.get());
                return 1;
            }
        }
         return 0;
    }

    @PutMapping("/Fiche/{id}")
    @ResponseBody
    public Map<String, String> updateFicheReject(@PathVariable int id, @RequestBody Fiche fiche) {
        Optional<Fiche> f = ficheService.getFiche(id);
        if(f.isPresent()) {
            Fiche current = f.get();
            if(current.getState() ==2 || current.getState()==0 ){
                current.setState(0);
                String semestre = fiche.getSemestre();
                if(semestre!= current.getSemestre()) current.setSemestre(semestre);
                String titre = fiche.getTitre();
                if(titre!= current.getTitre()) current.setTitre(titre);
                String date = fiche.getDate();
                if(date!= current.getDate()) current.setDate(date);
                else current.setDate(String.valueOf(LocalDate.now()));
                String heureDeDebut = fiche.getHeureDeDebut();
                if(heureDeDebut!=current.getHeureDeDebut()) current.setHeureDeDebut(heureDeDebut);
                String heureDeFin = fiche.getHeureDeFin();
                if(heureDeFin!=current.getHeureDeFin())current.setHeureDeFin(heureDeFin);
                 String totalHoraire = fiche.getTotalHoraire();
                if(totalHoraire!= current.getTotalHoraire())current.setTotalHoraire(totalHoraire);
                String contenu = fiche.getContenu();
                if(contenu!= current.getContenu()) current.setContenu(contenu);
                Enseignant enseignant = fiche.getEnseignant();
                if(enseignant!=current.getEnseignant())current.setEnseignant(enseignant);
                UniteEnseignement uniteEnseignement = fiche.getUe();
                if(uniteEnseignement!=current.getUe())current.setUe(uniteEnseignement);
                Niveau niveau = fiche.getNiveau();
                if(niveau!=current.getNiveau())current.setNiveau(niveau);
                Salle salle = fiche.getSalle();
                if(salle!=current.getSalle())current.setSalle(salle);
                Specialite specialite = fiche.getSpecialite();
                if(specialite!=current.getSpecialite())current.setSpecialite(specialite);
                Delegue delegue = fiche.getDelegue();
                if(delegue!=current.getDelegue())current.setDelegue(delegue);
                Seance seance = fiche.getSeance();
                if(seance!=current.getSeance())current.setSeance(seance);
                current.setState(0);
                current.setMotif(null);
                ficheService.saveFiche(current);
                return Collections.singletonMap("response", "Fiche modifiee avec succès");
            }
            else return  Collections.singletonMap("response", "Seuls les fiches en attente ou en cours de validation sont acceptées");
        } else {
            return Collections.singletonMap("response", "Fiche absente");
        }
    }

    @GetMapping("/fichebyenseignant/{enseignant_id}")
    @ResponseBody
    public Iterable<Fiche> findByIdEnseignant(@PathVariable("enseignant_id") Integer enseignant_id){
        return ficheService.findByIdEnseignant(enseignant_id);
    }

    @GetMapping("/fichebyenseignantandstate/{enseignant_id}/{state}")
    @ResponseBody
    public Iterable<Fiche> findByIdEnseignantAndState(@PathVariable("enseignant_id") Integer enseignant_id, @PathVariable("state")int state){
        return ficheService.findByEnseignantAndState(enseignant_id, state);
    }

    @GetMapping("/fichesdelegue/{delegue_id}")
    @ResponseBody
    public Iterable<Fiche> findByIdDelegue(@PathVariable("delegue_id") Integer delegue_id){
        return ficheService.findByDelegue(delegue_id);
    }

    @GetMapping("/fichedelegueandstate/{delegue_id}/{state}")
    @ResponseBody
    public Iterable<Fiche> findByIdDelegueAndState(@PathVariable("delegue_id") Integer delegue_id, @PathVariable("state") Integer state){
        return ficheService.findByDelegueAndState(delegue_id,state);
    }

    @GetMapping("/ficheweb/{id}")
    public String  allFiches(Model model, @PathVariable("id") int id )
    {


            Fiche fiche = ficheRepository.findFicheById(id);
            List<Delegue> delegue = delegueRepository.findAll();
            List<Niveau> niveau = niveauRepository.findAll();
            List<Salle> salle = salleRepository.findAll();
            List<Seance> seance = seanceRepository.findAll();
            List<Specialite> specialite = specialiteRepository.findAll();
            List<UniteEnseignement> uniteEnseignement = uniteEnseignementRepository.findAll();
            List<Enseignant> enseignant = enseignantRepository.findAll();

            model.addAttribute("fiche", fiche);
            model.addAttribute("enseignant", enseignant);
            model.addAttribute("delegue", delegue);
            model.addAttribute("niveau", niveau);
            model.addAttribute("salle", salle);
            model.addAttribute("seance", seance);
            model.addAttribute("specialite", specialite);
            model.addAttribute("ue", uniteEnseignement);



//niveauService.CreateNiveauToDB(code);// PostMapping
        return "/fiche.html";
    }



}
