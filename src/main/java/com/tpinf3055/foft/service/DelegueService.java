package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.Delegue;
import com.tpinf3055.foft.modele.Fiche;
import com.tpinf3055.foft.repository.DelegueRepository;
import com.tpinf3055.foft.repository.FicheRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class DelegueService {
    @Autowired
        private DelegueRepository delegueRepository;

    public Optional<Delegue> getDelegue(Integer id){
        return delegueRepository.findById(id);
    }

    public Iterable<Delegue> getdelegues(){
        return delegueRepository.findAll();
    }

    public void deleteDelegue (Integer id){
        delegueRepository.deleteById(id);
    }

    public Delegue saveDelegue (Delegue delegue){
        Delegue saved = delegueRepository.save(delegue);
        return saved;
    }
}
