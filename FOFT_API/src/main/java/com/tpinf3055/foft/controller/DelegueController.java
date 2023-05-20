package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.*;
import com.tpinf3055.foft.repository.*;
import com.tpinf3055.foft.service.DelegueService;
import com.tpinf3055.foft.service.SpecialiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
public class DelegueController {

    @Autowired
    private DelegueService delegueService;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private DelegueRepository delegueRepository;
    @Autowired
    private NiveauRepository niveauRepository;

    @Autowired
    private SpecialiteRepository specialiteRepository;
    @Autowired
    private SemestreRepository semestreRepository;
    @Autowired
    private FicheRepository ficheRepository;


    @PostMapping("/Delegue")
    @ResponseBody
    public Delegue createDelegue(@RequestBody Delegue delegue){
         return delegueService.saveDelegue(delegue);
    }
    @PostMapping("/createDelegue")
    @ResponseBody
    public Delegue createDelegue(@RequestParam("photo")MultipartFile photo,
                                 @RequestParam("nom")String nom,
                                 @RequestParam("email")String email,
                                 @RequestParam("matricule")String matricule,
                                 @RequestParam("niveau") Niveau niveau,
                                 @RequestParam("specialite")Specialite specialite) throws IOException {

        return delegueService.saveDelegueToDB(photo,nom, email, matricule, niveau, specialite);
    }


    @GetMapping("/Delegue/{id}")
    @ResponseBody
    public Delegue getDelegue(@PathVariable("id") final Integer id ){
        Optional<Delegue> delegue = delegueService.getDelegue(id);
        if(delegue.isPresent()) {
            return delegue.get();
        } else {
            return null;
        }
    }

    @GetMapping("/Delegue/{email}/{password}")
    @ResponseBody
    public Delegue getDelegueByAdminAndPassword(@PathVariable final String email,
                                            @PathVariable final String password){
        Optional<Delegue> delegue = delegueService.findDelegueByEmailAndPassword(email, password);
        if(delegue.isPresent()) {
            return delegue.get();
        } else {
            return null;
        }


    }

    @GetMapping("/Delegue")
    @ResponseBody
    public Iterable<Delegue> getDelegues() {
        return delegueService.getdelegues();
    }

    @GetMapping("/deleguebyniveauAndId/{specialiteId}/{niveauId}")
    @ResponseBody
    public List<Delegue> getDeleguesSpecialiteAndNiveau(@PathVariable("specialiteId") Integer specialiteId,
                                                        @PathVariable("niveauId") Integer niveauId ){
        return delegueService.getBySpecialiteAndNiveau(specialiteId, niveauId);
    }

    @DeleteMapping("/Delegue/{id}")
    @ResponseBody
    public void deleteDelegue(@PathVariable("id") final Integer id) {
        delegueService.deleteDelegue(id);
    }


    @PostMapping("/creatDelegue")
    public String  CreatDelegue(@RequestParam("file") MultipartFile file,
                                @RequestParam("nom") String nom,
                                @RequestParam("mail") String mail,
                                @RequestParam("matricule") String matricule,
                                @RequestParam("niveau") Niveau niveau,
                                @RequestParam("specialite") Specialite specialite) throws IOException {
        if(!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")){
            return "Image JPEG ou PNG requises"; // faire que ça retourne une page HTML d'érreur ; pensez à creer une méthode qui permet au chef d'ajouter sans photo
        }else if(delegueService.findByEmail(mail).isPresent() ){
            return "Le delegue existe déjà";
        }else delegueService.saveDelegueToDB(file, nom, mail, matricule, niveau,specialite);
        return "redirect:/ListDelegue";
    }
    @PostMapping("/changedelegue/{id}")
    public String changeDelegueAll(@PathVariable("id") int id,
                                   @RequestParam("newfile") MultipartFile file,
                                   @RequestParam("newnom") String nom,
                                   @RequestParam("newmail") String mail,
                                   @RequestParam("newmatricule") String matricule,
                                   @RequestParam("newpassword") String password) {
        delegueService.updateinformation(id,file, nom, mail, matricule, password);

        return "redirect:/ListDelegue";
    }

    @GetMapping("/changedelegueGet/{id}")
    public String changeAllGet()
    {
        // la page en question return "redirect:/listProducts.html";
        return "ChangeDelegue.html";
    }

    @PutMapping("/delegue/{id}")
    @ResponseBody
    public Map<String, String> updateDelegue(@PathVariable("id") int id,
                                             @RequestParam("photo") MultipartFile photo,
                                             @RequestParam("nom") String nom,
                                             @RequestParam("email") String mail,
                                             @RequestParam("matricule") String matricule) throws IOException {
        Optional<Delegue> f = delegueService.getDelegue(id);
        if(f.isPresent()) {
            Delegue current = f.get();
            if (photo!=null) {
                final String folder = new ClassPathResource("static/PhotoD/").getFile().getAbsolutePath();
                final String route = ServletUriComponentsBuilder.fromCurrentContextPath().path("/PhotoD/").path(photo.getOriginalFilename()).toUriString();
                byte[] bytes = photo.getBytes();
                System.out.println(route);
                Path path = Paths.get(folder + File.separator + photo.getOriginalFilename());
                Files.write(path, bytes);
                current.setPhoto("/PhotoD/"+photo.getOriginalFilename());
            }
            if(nom!= current.getNom()) current.setNom(nom);
            if(mail!= current.getEmail()) current.setEmail(mail);
            if(matricule!= current.getMatricule()) current.setMatricule(matricule);
            delegueService.saveDelegue(current);
            return Collections.singletonMap("response", "Compte modifié avec succès");
        }
        else {
            return Collections.singletonMap("response", "Compte absent");
        }
    }



    @GetMapping("/ListDelegue")
    public String  allDelegue(Model model)
    {
        List<Delegue> dels=delegueRepository.findAll();
        List<Niveau> niveaux=niveauRepository.findAll();
        List<Specialite> specialites = specialiteRepository.findAll();
        List<Semestre> semestres = semestreRepository.findAll();
        long delCount=delegueRepository.count();
        long ensCount=enseignantRepository.count();
        long ficheCount = ficheRepository.count();
        model.addAttribute("semestre", semestres);
        model.addAttribute("specialite", specialites);
        model.addAttribute("niveau", niveaux);
        model.addAttribute("dels", dels);
        model.addAttribute("delCount", delCount);
        model.addAttribute("ensCount", ensCount);
        model.addAttribute("ficheCount",ficheCount);
        return "/delegue.html";
    }

    @GetMapping("/deleteDelegue/{id}")
    public String deleteDelegue(@PathVariable("id") int id)
    {

        delegueService.deleteDelegue(id);
        return "redirect:/ListDelegue";
    }
}
