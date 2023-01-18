package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.Delegue;
import com.tpinf3055.foft.modele.Enseignant;
import com.tpinf3055.foft.repository.EnseignantRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Data
@Service
public class EnseignantService {
    @Autowired
    private EnseignantRepository enseignantRepository;

    public Optional<Enseignant> getEnseignant(Integer id){
        return enseignantRepository.findById(id);
    }

    public Enseignant saveEnseignantToDB(MultipartFile photo, String name, String email)
    {
        Enseignant enseignant = new Enseignant();
        String fileName = StringUtils.cleanPath(photo.getOriginalFilename());
        if(fileName.contains(".."))
        {
            System.out.println("not a a valid file");
        }
        try {
            enseignant.setPhoto(Base64.getEncoder().encodeToString(photo.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        enseignant.setEmail(email);

        enseignant.setNom(name);


        enseignantRepository.save(enseignant);
        return enseignant;
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
