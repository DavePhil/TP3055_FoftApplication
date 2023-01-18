package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Admin;
import com.tpinf3055.foft.modele.Enseignant;
import com.tpinf3055.foft.modele.Fiche;
import com.tpinf3055.foft.modele.UniteEnseignement;
import com.tpinf3055.foft.service.FicheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Optional;

@RestController
public class FicheController {

    @Autowired
    private FicheService ficheService;

    @PostMapping("/Fiche")
    public Fiche createFiche(@RequestBody Fiche fiche){
        return ficheService.saveFiche(fiche);
    }



    @PostMapping("/createFiche")
    public Fiche createFiche (@RequestParam("signature") MultipartFile signature,
                              @RequestParam("semestre")String semestre,
                              @RequestParam("titre")String titre,
                              @RequestParam("heureDebut")int heureDebut,
                              @RequestParam("heureFin")int heureFin,
                              @RequestParam("contenu") String contenu,
                              @RequestParam("totalHoraire") int totalHoraire,
                              @RequestParam("admin") Enseignant enseignant,
                              @RequestParam("uniteEnseignement")UniteEnseignement uniteEnseignement
                              ){

        return ficheService.saveFicheToDB(signature,semestre,titre,heureDebut,heureFin,contenu,totalHoraire,enseignant,uniteEnseignement);
    }

    @GetMapping("/Fiche/{id}")
    public Fiche getFiche(@PathVariable("id") final Integer id ){
        Optional<Fiche> fiche = ficheService.getFiche(id);
        if(fiche.isPresent()) {
            return fiche.get();
        } else {
            return null;
        }
    }

    @GetMapping("/Fiche/{id}/{state}")
    public Fiche getByIdAndState(@PathVariable int id, @PathVariable int state){
        Optional<Fiche> fiche = ficheService.getFicheByIdAndState(id,state);
        if(fiche.isPresent()) {
            return fiche.get();
        } else {
            return null;
        }
    }

    @GetMapping("/Fiche")
    public Iterable<Fiche> getFiches() {
        return ficheService.getFiches();
    }

    @DeleteMapping("/Fiche/{id}")
    public void deleteFiche(@PathVariable("id") final Integer id) {
        ficheService.deleteFiche(id);
    }

    @DeleteMapping("/Fiche/{id}/{state}")
    public void deleteIfReject(@PathVariable int id, @PathVariable int state){
        if(state==2) ficheService.deleteFicheByIdAndState(id, state);
        else return;
    }



    @PutMapping("/valideOrReject/{state}/{id}")
    public int acceptFiche(@PathVariable int state, @PathVariable Integer id) {
       return ficheService.valideOrRejectFiche(state, id);
    }

    @PutMapping("/updateFicheMotif")
    public int updateFicheMotif(@RequestParam String motif, @RequestParam Integer id ){
        Optional<Fiche> f = ficheService.getFiche(id);
        if(f.isPresent()){
            if (f.get().getState()==2){
                return ficheService.ajouteMotif(motif, id);
            }
        }
        return 0;
    }




    @PutMapping("/addSignatureEnseignant")
    public int addSignatureEnseignant (@RequestParam("signature") MultipartFile signature,
                                       @RequestParam("id")Integer id){
        Optional<Fiche> f = ficheService.getFiche(id);
        if(f.isPresent()){
            if(f.get().getState() == 0) {
                String fileName = StringUtils.cleanPath(signature.getOriginalFilename());
                if(fileName.contains(".."))
                {
                    System.out.println("not a a valid file");
                }
                try {
                    f.get().setSignatureEnseignant(Base64.getEncoder().encodeToString(signature.getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ficheService.saveFiche(f.get());
                return 1;
            }
        }
         return 0;
    }

    @PutMapping("/Fiche/{id}")
    public Fiche updateFicheReject(@PathVariable int id, @RequestBody Fiche fiche) {
        Optional<Fiche> f = ficheService.getFiche(id);
        if(f.isPresent()) {
            Fiche current = f.get();
            if(current.getState() ==2 ){
                String semestre = fiche.getSemestre();
                if(semestre!=null) current.setSemestre(semestre);
                String titre = fiche.getTitre();
                if(titre!=null) current.setTitre(titre);
                LocalDate date = fiche.getDate();
                if(date!=null) current.setDate(date);
                else current.setDate(LocalDate.now());
                int heureDeDebut = fiche.getHeureDeDebut();
                if(heureDeDebut!=0) current.setHeureDeDebut(heureDeDebut);
                int heureDeFin = fiche.getHeureDeFin();
                if(heureDeFin!=0) current.setHeureDeFin(heureDeFin);
                String contenu = fiche.getContenu();
                if(contenu!=null) current.setContenu(contenu);
                return ficheService.saveFiche(current);
            }
            else return null;
        } else {
            return null;
        }
    }




}
