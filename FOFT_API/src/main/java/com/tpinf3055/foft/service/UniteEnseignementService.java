package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.UniteEnseignement;
import com.tpinf3055.foft.repository.UniteEnseignementRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
