package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.Niveau;
import com.tpinf3055.foft.modele.Semestre;
import com.tpinf3055.foft.modele.Specialite;
import com.tpinf3055.foft.modele.UniteEnseignement;
import com.tpinf3055.foft.repository.UniteEnseignementRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class UniteEnseignementService {

    @Autowired
    private UniteEnseignementRepository uniteEnseignementRepository;

    public Optional<UniteEnseignement> getUniteEnseignement(final Integer id){
        return uniteEnseignementRepository.findById(id);
    }
    public Iterable<UniteEnseignement> getUniteEnseignements(){
        return  uniteEnseignementRepository.findAll();
    }
    public void deleteUniteEnseignement(final Integer id){
        uniteEnseignementRepository.deleteById(id);
    }

    public UniteEnseignement saveUniteEnseignement(UniteEnseignement uniteEnseignement){
        UniteEnseignement savedUniteEnseigment =uniteEnseignementRepository.save(uniteEnseignement);
        return savedUniteEnseigment;
    }
    public List<UniteEnseignement> getCodeUniteEnseignement(){
        return uniteEnseignementRepository.getCodeUniteEnseignement();
    }

    public List<UniteEnseignement> getUniteEnseignementsBySemestreAndNiveau( Integer semestre,  Integer niveau){

        return uniteEnseignementRepository.getAllBySemestreAndNiveau(semestre,niveau);
    }

    public void deleteUniteEnseignement( int id){
        try {
            uniteEnseignementRepository.deleteById(id);
        } catch (Exception e){

        }
    }


    public void  CreateUEToDB(String code, Niveau niveau, Semestre semestre, Specialite specialite)
    {
        UniteEnseignement ue=new UniteEnseignement();
        ue.setCode(code);
        ue.setNiveau(niveau);
        ue.setSemestre(semestre);
        ue.setSpecialite(specialite);
        uniteEnseignementRepository.save(ue);
    }
}
