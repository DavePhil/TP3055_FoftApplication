package com.tpinf3055.foft.modele;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;
import java.util.List;

@Data
@Entity
@DynamicUpdate
public class Fiche {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titre;
    private Date date;
    private String contenu;
    private String motif;
    private String signatureDelegue;
    private Boolean rattrapage;
    private String signatureEnseignant;
    @ManyToOne
    private Niveau niveau;
    @ManyToOne
    private Specialite specialite;
    @ManyToOne
    private Seance seance;
    @ManyToOne
    private Salle salle;
    @ManyToOne
    private Delegue delegue;


    private int state; // -x1 => initiés 0 => en cours de validation; 1 =>  rejetés; 2 => validés;  3 => Rattrapage

    @ManyToOne
    private Programme programme;

    public Fiche() {
        super();
        state=-1;
        contenu=" ";
        rattrapage = false;
    }


}
