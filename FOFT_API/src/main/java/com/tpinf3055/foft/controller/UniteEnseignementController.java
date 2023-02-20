package com.tpinf3055.foft.controller;

import com.tpinf3055.foft.modele.Niveau;
import com.tpinf3055.foft.modele.Semestre;
import com.tpinf3055.foft.modele.UniteEnseignement;
import com.tpinf3055.foft.repository.*;
import com.tpinf3055.foft.service.UniteEnseignementService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@Controller
public class UniteEnseignementController {
    @Autowired
    private UniteEnseignementService uniteEnseignementService;
    @Autowired
    private UniteEnseignementRepository uniteEnseignementRepository;
    @Autowired
    private DelegueRepository delegueRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private FicheRepository ficheRepository;
    @Autowired
    private NiveauRepository niveauRepository;
    @Autowired
    private SemestreRepository semestreRepository;

    @PostMapping("/UniteEnseignement")
    @ResponseBody
    public UniteEnseignement createUniteEnseignement(@RequestBody UniteEnseignement uniteEnseignement){
        return uniteEnseignementService.saveUniteEnseignement(uniteEnseignement);
    }

    @GetMapping("/UniteEnseignement/{id}")
    @ResponseBody
    public UniteEnseignement getUniteEnseignement(@PathVariable("id") final Integer id ){
        Optional<UniteEnseignement> uniteEnseignement = uniteEnseignementService.getUniteEnseignement(id);
        if(uniteEnseignement.isPresent()) {
            return uniteEnseignement.get();
        } else {
            return null;
        }
    }

    @GetMapping("/UniteEnseignement")
    @ResponseBody
    public Iterable<UniteEnseignement> getUniteEnseignements() {
        return uniteEnseignementService.getUniteEnseignements();
    }


    @DeleteMapping("/UniteEnseignement/{id}")
    @ResponseBody
    public void deleteUniteEnseignement(@PathVariable("id") final Integer id) {
        uniteEnseignementService.deleteUniteEnseignement(id);
    }

    @GetMapping("/getCodeUniteEnseignement")
    @ResponseBody
    public List<UniteEnseignement> getCodeUniteEnseignement(){
        return uniteEnseignementService.getCodeUniteEnseignement();
    }

    @GetMapping("/uniteEnseignements/{id_semestre}/{id_niveau}")
    @ResponseBody
    public Iterable<UniteEnseignement> getUniteEnseignements(@PathVariable("id_semestre") Integer semestre, @PathVariable("id_niveau") Integer niveau) {
        return uniteEnseignementService.getUniteEnseignementsBySemestreAndNiveau(semestre,niveau);
    }

    @GetMapping("/ajoutUe")
    public String addUe()
    {
        // lien vers le temolate   return "/login.html";
        return  "ok";
    }

    @PostMapping("/addUe")
    public String saveProduct(@RequestParam("file") MultipartFile file,
                              @RequestParam("code") String code,
                              @RequestParam("specialite_id") int specialite_id,
                              @RequestParam("niveau_id") int niveau_id,
                              @RequestParam("salle_id") int salle_id,
                              @RequestParam("admin_id") int admin_id)

    {
        UniteEnseignement ue=new UniteEnseignement();
        ue.setCode(code);

        uniteEnseignementService.saveUniteEnseignement(ue);
        return "redirect:/HomePage";
    }



    /**********---------------------------------------------------------------------------------------********************/
    @GetMapping("/ListUE")
    public String  allUE(Model model ) //@RequestParam("code") String code) // postMapping
    {
        List<UniteEnseignement>  ue=uniteEnseignementRepository.findAll();
        model.addAttribute("Ues", ue);
        long delCount=delegueRepository.count();
        List<Niveau>  niveaux=niveauRepository.findAll();
        model.addAttribute("niveau", niveaux);
        List<Semestre> semestre =semestreRepository.findAll();
        model.addAttribute("semestre", semestre);
        long ensCount=enseignantRepository.count();
        long fichecount = ficheRepository.count();

        model.addAttribute("delCount", delCount);
        model.addAttribute("ensCount", ensCount);
        model.addAttribute("fichecount",fichecount);


//niveauService.CreateNiveauToDB(code);// PostMapping
        return "/UE.html";
    }

    @GetMapping("/deleteUE/{id}")
    public String deleteNiveau(@PathVariable("id") int id)
    {
        uniteEnseignementService.deleteUniteEnseignement(id);
        return "redirect:/ListUE";
    }


    @PostMapping("/Createue")
    public String CreateuUE(@RequestParam("code") String code, @RequestParam("niveau") Niveau niveau,@RequestParam("semestre") Semestre semestre)
    {
        uniteEnseignementService.CreateUEToDB(code,niveau,semestre);
        return "redirect:/ListUE";

    }



}
