package com.tpinf3055.foft.modele;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Data
@Entity
@DynamicUpdate
public class Fiche {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String semestre;
    private String titre;
    private LocalDate date;
    private String heureDeDebut;
    private String heureDeFin;
    private String totalHoraire;
    private String contenu;
    private String signature;
    private int state; // 0 en cours de validation; 1 echec; 2 validé
    @ManyToOne
    private UniteEnseignement uniteEnseignement;
    @ManyToOne
    private Delegue delegue ;


    public Fiche(Integer id, String semestre, String titre, LocalDate date, String heureDeDebut, String heureDeFin, String totalHoraire, String contenu, String signature, UniteEnseignement uniteEnseignement, int state, Delegue delegue)
    {
        this.id = id;
        this.semestre = semestre;
        this.titre = titre;
        this.date = date;
        this.heureDeDebut = heureDeDebut;
        this.heureDeFin = heureDeFin;
        this.totalHoraire = totalHoraire;
        this.contenu = contenu;
        this.signature = signature;
        this.uniteEnseignement = uniteEnseignement;
        this.delegue = delegue;
        this.state = state;
    }

    public Fiche() {
        super();
        state=0;
        date = LocalDate.now();
    }


}
