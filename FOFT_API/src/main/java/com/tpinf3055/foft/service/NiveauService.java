package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.Niveau;
import com.tpinf3055.foft.repository.NiveauRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class NiveauService {
    @Autowired
    private NiveauRepository niveauRepository;

    public Optional<Niveau> getNiveau(Integer id){
        return niveauRepository.findById(id);
    }

    public Iterable<Niveau> getNiveaux(){
        return niveauRepository.findAll();
    }

    public void deleteNiveau (Integer id){
        try {
            niveauRepository.deleteById(id);
        } catch (Exception e){

        }
    }

    public Niveau saveNiveau (Niveau niveau){
        Niveau saved = niveauRepository.save(niveau);
        return saved;
    }
}
