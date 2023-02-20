package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.Salle;
import com.tpinf3055.foft.repository.SalleRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class SalleService {
    @Autowired
    private SalleRepository salleRepository;

    public Optional<Salle> getSalle(Integer id){
        return salleRepository.findById(id);
    }

    public Iterable<Salle> getSalles(){
        return salleRepository.findAll();
    }

    public void deleteSalle (Integer id){
        salleRepository.deleteById(id);
    }

    public Salle saveSalle (Salle specialite){
        Salle saved = salleRepository.save(specialite);
        return saved;
    }
    public Salle saveSalleToDB(String name)
    {
        Salle salle = new Salle();
        salle.setNom(name);

        salleRepository.save(salle);


        return salle;
    }
}
