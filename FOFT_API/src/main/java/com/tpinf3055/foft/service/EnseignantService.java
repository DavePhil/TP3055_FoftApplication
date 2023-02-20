package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.Delegue;
import com.tpinf3055.foft.modele.Enseignant;
import com.tpinf3055.foft.repository.EnseignantRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public Enseignant saveEnseignantToDB(MultipartFile photo, String name, String email) throws IOException {

        Enseignant enseignant = new Enseignant();
        final String folder = new ClassPathResource("static/PhotoE/").getFile().getAbsolutePath();
        final String route = ServletUriComponentsBuilder.fromCurrentContextPath().path("/PhotoE/").path(photo.getOriginalFilename()).toUriString();
        byte [] bytes = photo.getBytes();
        System.out.println(route);
        Path path = Paths.get(folder + File.separator + photo.getOriginalFilename());
        Files.write(path,bytes);
        enseignant.setEmail(email);
        enseignant.setPhoto("/PhotoE/"+photo.getOriginalFilename());
        enseignant.setNom(name);


        enseignantRepository.save(enseignant);
        return enseignant;
    }

    public Optional<Enseignant> findEnseignantByEmailAndPassword(String email, String password){
        Optional<Enseignant> enseignant = enseignantRepository.findByEmailAndPassword(email, password);
        if(enseignant.isPresent()){
            return enseignant;
        }
        else return null;
    }
    public Iterable<Enseignant> getEnseignants(){
        return enseignantRepository.findAll();
    }

//    public void deleteEnseignant (Integer id){
//        enseignantRepository.deleteById(id);
//    }

    public Enseignant saveEnseignant (Enseignant enseignant){
        Enseignant saved = enseignantRepository.save(enseignant);
        return saved;
    }

    public void deleteEnseignant (int id){
        try {
            enseignantRepository.deleteById(id);
        } catch (Exception e){

        }
    }


}
