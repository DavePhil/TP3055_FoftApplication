package com.tpinf3055.foft.service;


import com.tpinf3055.foft.modele.Niveau;
import com.tpinf3055.foft.repository.NiveauRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
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
        niveauRepository.deleteById(id);
    }

    public Niveau saveNiveau ( Niveau niveau){
        Niveau saved = niveauRepository.save(niveau);
        return saved;
    }
    public void  CreateNiveauToDB(String code)
    {
        Niveau niveau=new Niveau();
        niveau.setIntitule(code);
        niveauRepository.save(niveau);
    }

}
