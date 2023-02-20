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
    private String date;
    private String heureDeDebut;
    private String heureDeFin;
    private String totalHoraire;
    private String contenu;
    private String motif;
    @ManyToOne
    private Niveau niveau;
    @ManyToOne
    private Salle salle;
    @ManyToOne
    private Seance seance;
    private String signatureDelegue;
    private String signatureEnseignant;
    private int state; // 0 en cours de validation; 1 echec; 2 validé

    @ManyToOne
    private Enseignant enseignant;

    @ManyToOne
    private Delegue delegue;

    @ManyToOne
    private UniteEnseignement ue;
    @ManyToOne
    private Specialite specialite;


    public Fiche() {
        super();
        state=0;
    }


}
