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
public class Enseignant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    private String nom;
    private String email;
    private String motDePasse;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String photo;
    @OneToMany
    private Set<UniteEnseignement> uE ;

    public Enseignant(Integer id, String nom, String email, String motDePasse, Set<UniteEnseignement> uE) {
        super();
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.uE = uE;
    }

    public Enseignant() {
        super();
    }
}
