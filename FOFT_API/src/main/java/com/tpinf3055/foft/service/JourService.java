package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.Jour;
import com.tpinf3055.foft.repository.JourRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class JourService {
    @Autowired
    private JourRepository jourRepository;


    public Jour saveJour(Jour jour){
        Jour saved = jourRepository.save(jour);
        return saved;
    }

    public Optional<Jour> findJourById(int id){
        return jourRepository.findById(id);
    }

    public List<Jour> findJours(){
        return jourRepository.findAll();
    }
}
