package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.*;
import com.tpinf3055.foft.repository.*;
import com.tpinf3055.foft.service.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

    @Autowired
    NiveauRepository niveauRepository;
    @Autowired
    SemestreRepository semestreRepository;

    @Autowired
    SpecialiteRepository specialiteRepository;


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

    @PutMapping("/enseignant/{id}")
    @ResponseBody
    public Map<String, String> updateDelegue(@PathVariable("id") int id,
                                             @RequestParam("photo") MultipartFile photo,
                                             @RequestParam("nom") String nom,
                                             @RequestParam("email") String mail) throws IOException {
        Optional<Enseignant> f = enseignantService.getEnseignant(id);
        if(f.isPresent()) {
            Enseignant current = f.get();
            if (photo!=null) {
                final String folder = new ClassPathResource("static/PhotoE/").getFile().getAbsolutePath();
                final String route = ServletUriComponentsBuilder.fromCurrentContextPath().path("/PhotoE/").path(photo.getOriginalFilename()).toUriString();
                byte[] bytes = photo.getBytes();
                System.out.println(route);
                Path path = Paths.get(folder + File.separator + photo.getOriginalFilename());
                Files.write(path, bytes);
                current.setPhoto("/PhotoE/"+photo.getOriginalFilename());
            }
            if(nom!= current.getNom()) current.setNom(nom);
            if(mail!= current.getEmail()) current.setEmail(mail);
            enseignantService.saveEnseignant(current);
            return Collections.singletonMap("response", "Compte modifié avec succès");
        }
        else {
            return Collections.singletonMap("response", "Compte absent");
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
        List<Niveau> niveaux=niveauRepository.findAll();
        List<Specialite> specialites = specialiteRepository.findAll();
        List<Semestre> semestre =semestreRepository.findAll();
        model.addAttribute("semestre", semestre);

        model.addAttribute("specialite", specialites);
        model.addAttribute("niveau", niveaux);



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
        if(!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")){
            return "Image JPEG ou PNG requises"; // faire que ça retourne une page HTML d'érreur ; pensez à creer une méthode qui permet au chef d'ajouter sans photo
        }else if(enseignantService.findByEmail(mail).isPresent() ){
            return "L'enseignant existe déjà";
        } else enseignantService.saveEnseignantToDB(file, nom, mail);
        return "redirect:/ListEnseignant";
    }
}
