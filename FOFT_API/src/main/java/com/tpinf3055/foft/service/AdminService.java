package com.tpinf3055.foft.service;

import com.tpinf3055.foft.modele.Admin;
import com.tpinf3055.foft.repository.AdminRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.lang.model.element.AnnotationMirror;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;

@Data
@Service
public class AdminService {
    public Admin getByEmail;
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

    public Optional<Admin> findByEmail(String email){
        return adminRepository.findByEmail(email);
    }


    public Admin findAdminByEmailAndPassword(String mail, String password){
        return adminRepository.findByEmailAndPassword(mail,password);
    }

    public void  saveAdminToDB(MultipartFile file, String name, String mail) throws IOException {
        Admin admin = new Admin();
        final String folder = new ClassPathResource("static/PhotoD/").getFile().getAbsolutePath();
        final String route = ServletUriComponentsBuilder.fromCurrentContextPath().path("/PhotoD/").path(file.getOriginalFilename()).toUriString();
        byte [] bytes = file.getBytes();
        Path path = Paths.get(folder + File.separator +file.getOriginalFilename());
        Files.write(path,bytes);
        System.out.println(route);

        admin.setNom(name);
        admin.setEmail(mail);

        adminRepository.save(admin);
    }



}
