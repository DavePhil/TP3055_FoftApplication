package com.tpinf3055.foft.service;


import com.tpinf3055.foft.modele.Semestre;
import com.tpinf3055.foft.repository.SemestreRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
public class SemestreService {

    @Autowired
    private SemestreRepository semestreRepository;

    public Optional<Semestre> getSemestre(Integer id){
        return semestreRepository.findById(id);
    }

    public Iterable<Semestre> getSemestres(){
        return semestreRepository.findAll();
    }

    public void deleteSemestre (Integer id){
        semestreRepository.deleteById(id);
    }

    public Semestre saveSemestre ( Semestre semestre){
        Semestre saved = semestreRepository.save(semestre);
        return saved;
    }

}
