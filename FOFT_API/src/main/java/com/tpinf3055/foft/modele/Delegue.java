package com.tpinf3055.foft.modele;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@Data
@Entity
@DynamicUpdate
public class Delegue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String email;
    private String matricule;
    private String motDePasse;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String photo;
    private String niveau;
    @OneToMany
    @JoinColumn(name = "delegue_id", referencedColumnName = "id")
    private Set<Fiche> fiche;




}
