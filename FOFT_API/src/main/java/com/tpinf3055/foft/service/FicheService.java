package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.*;
import com.tpinf3055.foft.repository.FicheRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class FicheService {
    @Autowired
    private FicheRepository ficheRepository;

    public Optional<Fiche> getFiche(Integer id){
        return ficheRepository.findById(id);
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
    public Fiche saveFicheToDB(MultipartFile signature, String semestre, String titre ,
                               String heureDebut , String heureFin, String contenu, String totalHoraire,
                               Enseignant enseignant, UniteEnseignement uniteEnseignement, Niveau niveau, Salle salle, String date,Specialite specialite,Delegue delegue, Seance seance) throws IOException {
        Fiche fiche = new Fiche();
        // String folder = "E:\\Workspace1\\3ème Année\\Semestre1\\INF3055 - Conception Orientée Objet\\TP\\FOFT\\FOFT_API\\src\\main\\resources\\static\\SignatureD\\";
           final String folder = new ClassPathResource("static/SignatureD/").getFile().getAbsolutePath();

        final String route = ServletUriComponentsBuilder.fromCurrentContextPath().path("/SignatureD/").path(signature.getOriginalFilename()).toUriString();
           byte [] bytes = signature.getBytes();
           Path path = Paths.get(folder + File.separator + signature.getOriginalFilename());

        System.out.println(path);
           Files.write(path,bytes);
        System.out.println(route);
        fiche.setSignatureDelegue("/SignatureD/"+signature.getOriginalFilename());
        fiche.setContenu(contenu);
        fiche.setHeureDeFin(heureFin);
        fiche.setHeureDeDebut(heureDebut);
        fiche.setSemestre(semestre);
        fiche.setTitre(titre);
        fiche.setTotalHoraire(totalHoraire);
        fiche.setEnseignant(enseignant);
        fiche.setUe(uniteEnseignement);
        fiche.setNiveau(niveau);
        fiche.setSalle(salle);
        fiche.setDate(date);
        fiche.setSeance(seance);
        fiche.setDelegue(delegue);
        fiche.setSpecialite(specialite);



        ficheRepository.save(fiche);

        return fiche;
    }

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

    public List<Fiche> findByEnseignantAndState(Integer id_enseignant, Integer state){
        return ficheRepository.findByEnseignantAndState(id_enseignant, state);
    }

    public List<Fiche> findByDelegueAndState(Integer id_delegue, Integer state){
        return ficheRepository.findByDelegueAndState(id_delegue, state);
    }

    public List<Fiche> findByDelegue (Integer id_delegue){
        return ficheRepository.findByDelegue(id_delegue);
    }


//
//    public void ajouteMotif(Integer id, String motif){
//        ficheRepository.ajouteMotif(id, motif);
//    }


}
