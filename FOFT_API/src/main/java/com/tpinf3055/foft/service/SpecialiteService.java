package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.Specialite;
import com.tpinf3055.foft.repository.SpecialiteRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class SpecialiteService {

    @Autowired
    private SpecialiteRepository specialiteRepository;

    public Optional<Specialite> getSpecialite(Integer id){
        return specialiteRepository.findById(id);
    }

    public Iterable<Specialite> getSpecialites(){
        return specialiteRepository.findAll();
    }

    public void deleteSpecialite (Integer id){
        specialiteRepository.deleteById(id);
    }

    public Specialite saveSpecialite (Specialite specialite){
        Specialite savedSpecialite = specialiteRepository.save(specialite);
        return savedSpecialite;
    }

}
