package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.Enseignant;
import com.tpinf3055.foft.modele.Fiche;
import com.tpinf3055.foft.repository.EnseignantRepository;
import com.tpinf3055.foft.repository.FicheRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class EnseignantService {
    @Autowired
    private EnseignantRepository enseignantRepository;

    public Optional<Enseignant> getEnseignant(Integer id){
        return enseignantRepository.findById(id);
    }

    public Iterable<Enseignant> getEnseignants(){
        return enseignantRepository.findAll();
    }

    public void deleteEnseignant (Integer id){
        enseignantRepository.deleteById(id);
    }

    public Enseignant saveEnseignant (Enseignant enseignant){
        Enseignant saved = enseignantRepository.save(enseignant);
        return saved;
    }
}
