package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.Delegue;
import com.tpinf3055.foft.modele.Niveau;
import com.tpinf3055.foft.repository.DelegueRepository;
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
public class DelegueService {
    @Autowired
        private DelegueRepository delegueRepository;

    public Optional<Delegue> getDelegue(Integer id){

        Optional<Delegue> delegue = delegueRepository.findById(id);
        if(delegue.isPresent()){
            return delegue;
        }
        else return null;
    }

    public Delegue saveDelegueToDB(MultipartFile photo, String name, String email, String matricule, Niveau niveau) throws IOException {
        Delegue delegue = new Delegue();
        final String folder = new ClassPathResource("static/PhotoD/").getFile().getAbsolutePath();
        final String route = ServletUriComponentsBuilder.fromCurrentContextPath().path("/PhotoD/").path(photo.getOriginalFilename()).toUriString();
        byte [] bytes = photo.getBytes();
        Path path = Paths.get(folder + File.separator +photo.getOriginalFilename());
        Files.write(path,bytes);
        System.out.println(route);
        delegue.setEmail(email);
        delegue.setPhoto("/PhotoD/"+photo.getOriginalFilename());
        delegue.setNom(name);
        delegue.setNiveau(niveau);
        delegue.setMatricule(matricule);

        delegueRepository.save(delegue);
        return delegue;
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

    public Optional<Delegue> findDelegueByEmailAndPassword(String email, String password){
        Optional<Delegue> delegue = delegueRepository.findByEmailAndPassword(email, password);
        if(delegue.isPresent()){
            return delegue;
        }
        else return null;
    }


    public void updateinformation(int id,MultipartFile photo, String name, String email, String matricule, String niveau,String password)
    {
        Delegue del=new Delegue();
        del=delegueRepository.findById(id).get();
        del.setPhoto(String.valueOf(photo));
        del.setNom(name);
        del.setEmail(email);
        del.setMatricule(matricule);
        del.setPassword(password);
        delegueRepository.save(del);
    }

}
