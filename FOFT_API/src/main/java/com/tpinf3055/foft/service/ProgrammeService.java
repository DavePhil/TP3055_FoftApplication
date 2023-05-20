package com.tpinf3055.foft.service;


import com.tpinf3055.foft.modele.*;
import com.tpinf3055.foft.repository.DelegueRepository;
import com.tpinf3055.foft.repository.ProgrammeRepository;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class ProgrammeService {
    @Autowired
    private ProgrammeRepository programmeRepository;

    @Autowired
    private DelegueRepository delegueRepository;

    @Autowired
    private FicheService ficheService;

    public Optional<Programme> getProgramme(Integer id){
        return programmeRepository.findById(id);
    }
    public Programme getprogramme(Integer id ){return  programmeRepository.getById(id);}

    public Iterable<Programme> getProgrammes(){
        return programmeRepository.findAll();
    }
//
//    public List<Programme> getprogrammes(){return  programmeRepository.findAll();}

    public void deleteProgramme (Integer id){
        programmeRepository.deleteById(id);
    }

    public List<Programme> findBySpecialiteAndNiveau(int idSpecialite, int idNiveau, int idJour , int semestreId ){
        return programmeRepository.findProgrammeByNiveauAndSpecialite(idSpecialite,idNiveau, idJour,semestreId);
    }

    public Programme saveProgramme ( Programme programme){
        return programmeRepository.save(programme);
    }

    public Programme updateProgramme( int id, LocalTime heureDeDebut, LocalTime heureDeFin, String totalHoraire, Enseignant enseignant, UniteEnseignement uniteEnseignement, Salle salle, Seance seance, Jour jour){
        Programme current = getProgramme(id).get();
        if(heureDeFin!=null)current.setHeureDeFin(heureDeFin);
        if(heureDeDebut!=null) current.setHeureDeDebut(heureDeDebut);
        if(totalHoraire!=null) current.setTotalHoraire(totalHoraire);
        if(enseignant!=null) current.setEnseignant(enseignant);
        if (uniteEnseignement!=null) current.setUe(uniteEnseignement);
        if(salle!=null) current.setSalle(salle);
        if(seance!=null) current.setSeance(seance);
        if(jour!=null) current.setJour(jour);
        return saveProgramme(current);
    }

    public int idOfJour (String jour){
        //  System.out.println(id);
        return switch (jour) {
            case "lundi" -> 1;
            case "mardi" -> 2;
            case "mercredi" -> 3;
            case "jeudi" -> 4;
            case "vendredi" -> 5;
            case "samedi" -> 6;
            case "dimanche" -> 7;
            default -> 0;
        };
    }


    public List<Programme> findByJour (int jourId) {return programmeRepository.findProgrammeByJour(jourId);}


    Date aujourdhui = new Date();
    private static final SimpleDateFormat formater = new SimpleDateFormat("EEEE");
    private static final SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm");
    String jour = formater.format(aujourdhui);




    @Scheduled(cron = "0 59 23 * * ?") // Exécute tous les jours à 23 h 59 min
    public void planifierRattrapage() {
        List<Fiche> fiches = ficheService.findByState(0);
        for(Fiche fiche : fiches) fiche.setState(3);
    }
//    @Scheduled(cron = "0 59 23 * * L") // Exécute le dernier jour de la semaine à 23 h 59 min
//    public void planifierEchec() {
//        List<Fiche> fiches = ficheService.findByState(0);
//        for(Fiche fiche : fiches) fiche.setState(3);
//    }

    @Scheduled(fixedRate = 29000) // Exécute toutes les 30 secondes
    public void genererFiche() throws ParseException {

        List<Programme> programmes = findByJour(idOfJour(jour));
        for (Programme programme : programmes){
            String heureDebut = heureFormat.format(heureFormat.parse(programme.getHeureDeDebut().toString()));
//            int specialiteId = programme.getUe().getSpecialite().getId();
//            int niveauId = programme.getUe().getNiveau().getId();
            if(heureDebut.equals(heureFormat.format(new Date()))){
                //List<Delegue> delegues = delegueRepository.getAllBySpecialiteAndNiveau(specialiteId,niveauId);
                    ficheService.createFiche(programme,programme.getUe().getNiveau(), programme.getUe().getSpecialite(), programme.getSeance());
            }
        }


    }

}



