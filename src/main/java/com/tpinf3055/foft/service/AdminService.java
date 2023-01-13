package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.Admin;
import com.tpinf3055.foft.modele.Fiche;
import com.tpinf3055.foft.repository.AdminRepository;
import com.tpinf3055.foft.repository.FicheRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Optional<Admin> getadmin(Integer id){
        return adminRepository.findById(id);
    }

    public Iterable<Admin> getAdmins(){
        return adminRepository.findAll();
    }

    public void deleteAdmin (Integer id){
        adminRepository.deleteById(id);
    }

    public Admin saveAdmin (Admin admin){
        Admin saved = adminRepository.save(admin);
        return saved;
    }

}
