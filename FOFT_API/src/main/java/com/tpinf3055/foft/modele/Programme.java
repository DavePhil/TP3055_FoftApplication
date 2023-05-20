package com.tpinf3055.foft.modele;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@DynamicUpdate
public class Programme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalTime heureDeDebut;
    private LocalTime heureDeFin;
    private String totalHoraire;
    @ManyToOne
    private Enseignant enseignant;
    @ManyToOne
    private UniteEnseignement ue;
    @ManyToOne
    private Salle salle;
    @ManyToOne
    private Seance seance;
    @ManyToOne
    private Jour jour;




    public Programme() {
        super();
    }
}
