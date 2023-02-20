package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Admin;
import com.tpinf3055.foft.modele.Delegue;
import com.tpinf3055.foft.modele.Niveau;
import com.tpinf3055.foft.repository.*;
import com.tpinf3055.foft.service.DelegueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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
                                 @RequestParam("niveau") Niveau niveau) throws IOException {

        return delegueService.saveDelegueToDB(photo,nom, email, matricule, niveau);
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
                                @RequestParam("niveau") Niveau nivau) throws IOException {
        delegueService.saveDelegueToDB(file, nom, mail, matricule, nivau);
        // la page en question return "redirect:/listProducts.html";
        //  return "yoyo";
        return "redirect:/ListDelegue";
    }
    @PostMapping("/changedelegue/{id}")
    public String changeDelegueAll(@PathVariable("id") int id,
                                   @RequestParam("newfile") MultipartFile file,
                                   @RequestParam("newnom") String nom,
                                   @RequestParam("newmail") String mail,
                                   @RequestParam("newmatricule") String matricule,
                                   @RequestParam("newniveau") String niveau,
                                   @RequestParam("newpassword") String password) {
        delegueService.updateinformation(id,file, nom, mail, matricule, niveau, password);
        // la page en question return "redirect:/listProducts.html";
        return "redirect:/ListDelegue";
    }

    @GetMapping("/changedelegueGet/{id}")
    public String changeAllGet()
    {
        // la page en question return "redirect:/listProducts.html";
        return "ChangeDelegue.html";
    }



    @GetMapping("/ListDelegue")
    public String  allDelegue(Model model )
    {

        List<Delegue> dels=delegueRepository.findAll();
        List<Niveau> niveaux=niveauRepository.findAll();
        long delCount=delegueRepository.count();
        long ensCount=enseignantRepository.count();
        long ficheCount = ficheRepository.count();
        model.addAttribute("dels", dels);
        model.addAttribute("niveau", niveaux);
        model.addAttribute("delCount", delCount);
        model.addAttribute("ensCount", ensCount);
        model.addAttribute("ficheCount",ficheCount);



//niveauService.CreateNiveauToDB(code);// PostMapping
        return "/delegue.html";
    }

    @GetMapping("/deleteDelegue/{id}")
    public String deleteDelegue(@PathVariable("id") int id)
    {

        delegueService.deleteDelegue(id);
        return "redirect:/ListDelegue";
    }
}
