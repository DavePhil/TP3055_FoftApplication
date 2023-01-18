package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.Admin;
import com.tpinf3055.foft.modele.Enseignant;
import com.tpinf3055.foft.modele.Fiche;
import com.tpinf3055.foft.modele.UniteEnseignement;
import com.tpinf3055.foft.repository.FicheRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Base64;
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
                               int heureDebut , int heureFin, String contenu, int totalHoraire,
                               Enseignant enseignant, UniteEnseignement uniteEnseignement)
    {
        Fiche fiche = new Fiche();
        String fileName = StringUtils.cleanPath(signature.getOriginalFilename());
        if(fileName.contains(".."))
        {
            System.out.println("not a a valid file");
        }
        try {
            fiche.setSignatureDelegue(Base64.getEncoder().encodeToString(signature.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        fiche.setContenu(contenu);
        fiche.setHeureDeFin(heureFin);
        fiche.setHeureDeDebut(heureDebut);
        fiche.setSemestre(semestre);
        fiche.setTitre(titre);
        fiche.setTotalHoraire(totalHoraire);
        fiche.setEnseignant(enseignant);
        fiche.setUe(uniteEnseignement);


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


//
//    public void ajouteMotif(Integer id, String motif){
//        ficheRepository.ajouteMotif(id, motif);
//    }


}
