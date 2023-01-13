package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.Fiche;
import com.tpinf3055.foft.modele.Specialite;
import com.tpinf3055.foft.repository.FicheRepository;
import com.tpinf3055.foft.repository.SpecialiteRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Fiche saveFiche (Fiche fiche){
        Fiche saved = ficheRepository.save(fiche);
        return saved;
    }
}
