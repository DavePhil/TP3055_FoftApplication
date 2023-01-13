package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Admin;
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


}
