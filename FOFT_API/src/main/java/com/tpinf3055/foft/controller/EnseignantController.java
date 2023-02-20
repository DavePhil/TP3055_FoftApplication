package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Delegue;
import com.tpinf3055.foft.modele.Enseignant;
import com.tpinf3055.foft.repository.DelegueRepository;
import com.tpinf3055.foft.repository.EnseignantRepository;
import com.tpinf3055.foft.repository.FicheRepository;
import com.tpinf3055.foft.service.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class EnseignantController {

    @Autowired
    private EnseignantService enseignantService;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private DelegueRepository delegueRepository ;
    @Autowired
    private FicheRepository ficheRepository;


    @PostMapping("/Enseignant")
    @ResponseBody
    public Enseignant createEnseignant(@RequestBody Enseignant enseignant){
        return enseignantService.saveEnseignant(enseignant);
    }

    @PostMapping("/createEnseignant")
    @ResponseBody
    public Enseignant createEnseignant(@RequestParam("photo") MultipartFile photo,
                                 @RequestParam("nom")String nom,
                                 @RequestParam("email")String email) throws IOException {

        return enseignantService.saveEnseignantToDB(photo,nom, email);
    }
    @GetMapping("/enseignant/{email}/{password}")
    @ResponseBody
    public Enseignant getEnseignantByAdminAndPassword(@PathVariable final String email,
                                                @PathVariable final String password){
        Optional<Enseignant> enseignant = enseignantService.findEnseignantByEmailAndPassword(email, password);
        if(enseignant.isPresent()) {
            return enseignant.get();
        } else {
            return null;
        }


    }

    @GetMapping("/Enseignant/{id}")
    @ResponseBody
    public Enseignant getEnseignant(@PathVariable("id") final Integer id ){
        Optional<Enseignant> enseignant = enseignantService.getEnseignant(id);
        if(enseignant.isPresent()) {
            return enseignant.get();
        } else {
            return null;
        }
    }

    @GetMapping("/Enseignant")
    @ResponseBody
    public Iterable<Enseignant> getEnseignants() {
        return enseignantService.getEnseignants();
    }

    @DeleteMapping("/Enseignant/{id}")
    @ResponseBody
    public void deleteEnseignant(@PathVariable("id") final Integer id) {
        enseignantService.deleteEnseignant(id);
    }

    @PutMapping("/Enseignant/{id}")
    @ResponseBody
    public Enseignant updateEmployee(@PathVariable("id") final int id, @RequestBody Enseignant enseignant) {
        Optional<Enseignant> e = enseignantService.getEnseignant(id);
        if(e.isPresent()) {
            Enseignant current = e.get();

            String nom = enseignant.getNom();
            if(nom != null) {
                current.setNom(nom);
            }
            String mail = enseignant.getEmail();
            if(mail != null) {
                current.setEmail(mail);
            }
            String password = enseignant.getPassword();
            if(password != null) {
                current.setPassword(password);;
            }
            enseignantService.saveEnseignant(current);
            return current;
        } else {
            return null;
        }
    }

    @GetMapping("/ListEnseignant")
    public String  allEnseigant(Model model )
    {
        List<Enseignant> ens = enseignantRepository.findAll();
        long delCount=delegueRepository.count();
        long ensCount=enseignantRepository.count();
        long fichecount = ficheRepository.count();

        model.addAttribute("delCount", delCount);
        model.addAttribute("ensCount", ensCount);
        model.addAttribute("fichecount",fichecount);
        model.addAttribute("ens", ens);



//niveauService.CreateNiveauToDB(code);// PostMapping
        return "/enseignant.html";
    }

    @GetMapping("/delete_enseignant/{id}")
    public String deleteEnseignant(@PathVariable("id") int id)
    {

        enseignantService.deleteEnseignant(id);
        return "redirect:/ListEnseignant";
    }

    @PostMapping("/create_enseignant")
    public String  CreatEnseignant(@RequestParam("file") MultipartFile file,
                                   @RequestParam("nom") String nom,
                                   @RequestParam("mail") String mail) throws IOException {
        enseignantService.saveEnseignantToDB(file, nom, mail);
        // la page en question return "redirect:/listProducts.html";
        //  return "yoyo";
        return "redirect:/ListEnseignant";
    }
}
