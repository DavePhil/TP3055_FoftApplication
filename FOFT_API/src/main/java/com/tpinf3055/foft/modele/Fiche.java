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
    private int heureDeDebut;
    private int heureDeFin;
    private int totalHoraire;
    private String contenu;
    private String motif;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String signatureDelegue;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String signatureEnseignant;
    private int state; // 0 en cours de validation; 1 echec; 2 validé

    @ManyToOne
    private Enseignant enseignant;

    @ManyToOne
    private UniteEnseignement ue;


    public Fiche() {
        super();
        state=0;
        date = LocalDate.now();
    }


}
