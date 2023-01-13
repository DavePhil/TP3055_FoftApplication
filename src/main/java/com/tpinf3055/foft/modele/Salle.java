package com.tpinf3055.foft.modele;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@DynamicUpdate
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;

    @OneToMany
    private Set<UniteEnseignement> uE ;


    public Salle(Integer id, String nom, Set<UniteEnseignement> uE) {
        super();
        this.id = id;
        this.nom = nom;
        this.uE = uE;
    }

    public Salle() {
        super();
    }


}
