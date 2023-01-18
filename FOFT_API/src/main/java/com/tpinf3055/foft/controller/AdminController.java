package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Admin;
import com.tpinf3055.foft.modele.Enseignant;
import com.tpinf3055.foft.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/Admin")
    public Admin createAdmin(@RequestBody Admin admin){
        return adminService.saveAdmin(admin);
    }

    @GetMapping("/Admin/{id}")
    public Admin getAdmin(@PathVariable("id") final Integer id ){
        Optional<Admin> admin = adminService.getadmin(id);
        if(admin.isPresent()) {
            return admin.get();
        } else {
            return null;
        }
    }

    @GetMapping("/Admin")
    public Iterable<Admin> getAdmins() {
        return adminService.getAdmins();
    }

    @GetMapping("/getAdminByEmail/{email}/{password}")
    public  Admin getAdminByEmailAndPassWord(@PathVariable String email,
                                             @PathVariable String password){
        return adminService.findAdminByEmailAndPassword(email,password);

    }

    @PutMapping("/Admin/{id}")
    public Admin updateAdmin(@PathVariable("id") final int id, @RequestBody Admin admin) {
        Optional<Admin> e = adminService.getadmin(id);
        if(e.isPresent()) {
            Admin current = e.get();

            String nom = admin.getNom();
            if(nom != null) current.setNom(nom);
            String mail = admin.getEmail();
            if(mail != null) current.setEmail(mail);
            String password = admin.getPassword();
            if(password != null) current.setPassword(password);;
            adminService.saveAdmin(current);
            return current;
        } else {
            return null;
        }
    }







}
