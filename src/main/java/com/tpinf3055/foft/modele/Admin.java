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
public class Admin {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String nom;
        private String email;
        private String motDePasse;
        @Lob
        @Column(columnDefinition = "MEDIUMBLOB")
        private String photo;

        @OneToMany
        private Set<Fiche> fiche;
        @OneToMany
        private Set<Specialite> specialites;
        @OneToMany
        private Set<Salle> salles ;




}
