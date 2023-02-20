package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.*;
import com.tpinf3055.foft.repository.*;
import com.tpinf3055.foft.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private DelegueRepository delegueRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private NiveauRepository niveauRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private SpecialiteRepository specialiteRepository;
    @Autowired
    private UniteEnseignementRepository uniteEnseignementRepository;
    @Autowired
    private FicheRepository ficheRepository;

    @PostMapping("/Admin")
    @ResponseBody
    public Admin createAdmin(@RequestBody Admin admin){
        return adminService.saveAdmin(admin);
    }

    @GetMapping("/Admin/{id}")
    @ResponseBody
    public Admin getAdmin(@PathVariable("id") final Integer id ){
        Optional<Admin> admin = adminService.getadmin(id);
        if(admin.isPresent()) {
            return admin.get();
        } else {
            return null;
        }
    }

    @GetMapping("/Admin")
    @ResponseBody
    public Iterable<Admin> getAdmins() {
        return adminService.getAdmins();
    }

    @GetMapping("/getAdminByEmail/{email}/{password}")
    @ResponseBody
    public  Admin getAdminByEmailAndPassWord(@PathVariable String email,
                                             @PathVariable String password){
        return adminService.findAdminByEmailAndPassword(email,password);

    }

    @PutMapping("/Admin/{id}")
    @ResponseBody
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

    /* manage request admin -------------------*/
    @GetMapping("/HomePage")
    public String HomePagE(Model model)//,@PathVariable("si") int id)
    {        long delCount=delegueRepository.count();
        long ensCount=enseignantRepository.count();
        model.addAttribute("delCount", delCount);
        model.addAttribute("ensCount", ensCount);

        //model.addAttribute("userid", id);
        List<Delegue> delegues=delegueRepository.findAll();
        List<Niveau> niveaux=niveauRepository.findAll();
        List<Salle> salles=salleRepository.findAll();
        List<Fiche> fiches = ficheRepository.findAll();
        List<Seance> seances=seanceRepository.findAll();
        List<Specialite> specialites=specialiteRepository.findAll();
        List<UniteEnseignement> uniteEnseignements = uniteEnseignementRepository.findAll();
        List<Enseignant> enseignants = enseignantRepository.findAll();
        long ficheCount = ficheRepository.count();
        model.addAttribute("niveau", niveaux);
        model.addAttribute("delCount", delCount);
        model.addAttribute("ensCount", ensCount);
        model.addAttribute("ficheCount", ficheCount);
        model.addAttribute("fiches",fiches);
        model.addAttribute("enseignants",enseignants);
        model.addAttribute("delegue", delegues);
        model.addAttribute("niveau", niveaux);
        model.addAttribute("salle", salles);
        model.addAttribute("seance", seances);
        model.addAttribute("specialite", specialites);
        model.addAttribute("ue", uniteEnseignements);
        System.out.println(ficheCount);
        return "/index.html";
    }

    @GetMapping("/")
    public  String getAdminByEmailAndPassWord()
    {
        return "login.html";
    }
    @PostMapping("/login")
    public  String getAdminByEmailAndPassWord(Model model,@RequestParam("password") String password,@RequestParam("mail") String mail){
        Admin admin =adminService.findAdminByEmailAndPassword(mail, password);
        if(admin==null)
            return "redirect:/";
        else
            admin.getId();
        model.addAttribute("userid",admin.getId());
        return "redirect:/HomePage";
    }


    @PostMapping("/CreateAdmin")
    public String saveProduct(@RequestParam("file") MultipartFile file,
                              @RequestParam("nom") String nom,
                              @RequestParam("password") String password,
                              @RequestParam("mail") String mail) throws IOException {
        adminService.saveAdminToDB(file,nom,mail,password);
        return "redirect:/";
    }
    @GetMapping("/Register")
    public String RegisterAdmin()
    { return "Register.html"; }






}
