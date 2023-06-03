package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.*;
import com.tpinf3055.foft.repository.FicheRepository;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class FicheService {
    @Autowired
    private FicheRepository ficheRepository;
    @Autowired
    private SeanceService seanceService;
    public Optional<Fiche> getFiche(Integer id){
        return ficheRepository.findById(id);
    }

    public void createFiche (Programme programme, Niveau niveau, Specialite specialite, Seance seance){
        Fiche fiche = new Fiche();
        fiche.setProgramme(programme);
        fiche.setTitre(programme.getSeance().getNom() + " de "+ programme.getUe().getCode());
        fiche.setDate(new Date());
        fiche.setNiveau(niveau);
        fiche.setSpecialite(specialite);
        fiche.setSeance(seance);
        ficheRepository.save(fiche);
    }

    public Iterable<Fiche> getFiches(){
        return ficheRepository.findAll();
    }

    public void deleteFiche (Integer id){
        ficheRepository.deleteById(id);
    }

    public void deleteFicheByIdAndState(Integer id, Integer state){ficheRepository.deleteByIdAndState(id,state);}

    public Fiche saveFiche (Fiche fiche){
        Fiche saved = ficheRepository.save(fiche);
        return saved;
    }
    public Optional<Fiche> getFicheByIdAndState(int id, int state){
        return ficheRepository.findFicheByIdAndState(id,state);
    }
//    public Fiche saveFicheToDB(MultipartFile signature, String semestre, String titre ,
//                               String heureDebut , String heureFin, String contenu, String totalHoraire,
//                               Enseignant enseignant, UniteEnseignement uniteEnseignement, Niveau niveau, Salle salle, String date,Specialite specialite,Delegue delegue, Seance seance) throws IOException {
//        Fiche fiche = new Fiche();
//        // String folder = "E:\\Workspace1\\3ème Année\\Semestre1\\INF3055 - Conception Orientée Objet\\TP\\FOFT\\FOFT_API\\src\\main\\resources\\static\\SignatureD\\";
//           final String folder = new ClassPathResource("static/SignatureD/").getFile().getAbsolutePath();
//
//        final String route = ServletUriComponentsBuilder.fromCurrentContextPath().path("/SignatureD/").path(signature.getOriginalFilename()).toUriString();
//           byte [] bytes = signature.getBytes();
//           Path path = Paths.get(folder + File.separator + signature.getOriginalFilename());
//
//        System.out.println(path);
//           Files.write(path,bytes);
//        System.out.println(route);
//        fiche.setSignatureDelegue("/SignatureD/"+signature.getOriginalFilename());
//        fiche.setContenu(contenu);
//        fiche.setHeureDeFin(heureFin);
//        fiche.setHeureDeDebut(heureDebut);
//        fiche.setSemestre(semestre);
//        fiche.setTitre(titre);
//        fiche.setTotalHoraire(totalHoraire);
//        fiche.setEnseignant(enseignant);
//        fiche.setUe(uniteEnseignement);
//        fiche.setNiveau(niveau);
//        fiche.setSalle(salle);
//        fiche.setDate(date);
//        fiche.setSeance(seance);
//        fiche.setDelegue(delegue);
//        fiche.setSpecialite(specialite);
//
//
//
//        ficheRepository.save(fiche);
//
//        return fiche;
//    }

    public int valideOrRejectFiche(int state, Integer id){
       return ficheRepository.valideOrReject(state, id);
    }

    public int ajouteMotif(String motif, int id){
        return ficheRepository.ajouteMotif(motif,id);
    }

    public int ajouteSignature(MultipartFile signature, int id){
        return ficheRepository.ajouteSignature(signature,id);
    }

    public List<Fiche> findByIdEnseignant(Integer id_enseignant){
        return ficheRepository.findByEnseignant(id_enseignant);
    }

    public List<Fiche> findByEnseignantAndState(Integer id_enseignant, int state){
        return ficheRepository.findByEnseignantAndState(id_enseignant, state);
    }

    public List<Fiche> findByDelegueAndState(Integer niveau_id, Integer specialite_id, int state){
        return ficheRepository.findByDelegueAndState(niveau_id, specialite_id, state);
    }

    public List<Fiche> findByDelegue (Integer id_delegue){
        return ficheRepository.findByDelegue(id_delegue);
    }

    public  List<Fiche> findByState (Integer state) {return ficheRepository.findByState(state);}

    public  int countFiche(Integer enseignant_id){

        int nombreFicheCM = ficheRepository.countFicheBySignatureEnseignant(enseignant_id,1);
        int nombreFicheTD = ficheRepository.countFicheBySignatureEnseignant(enseignant_id,2);
        return (nombreFicheTD*2) + (nombreFicheCM*3);
    }


}
