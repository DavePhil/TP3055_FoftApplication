package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.Delegue;
import com.tpinf3055.foft.repository.DelegueRepository;
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

    public Delegue saveProductToDB(MultipartFile photo, String name, String email, String matricule, String niveau)
    {
        Delegue delegue = new Delegue();
        String fileName = StringUtils.cleanPath(photo.getOriginalFilename());
        if(fileName.contains(".."))
        {
            System.out.println("not a a valid file");
        }
        try {
            delegue.setPhoto(Base64.getEncoder().encodeToString(photo.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        delegue.setEmail(email);

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
}
