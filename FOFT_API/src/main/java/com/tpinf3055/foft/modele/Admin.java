package com.tpinf3055.foft.modele;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.nio.MappedByteBuffer;
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
        private String password;
        @Lob
        @Column(columnDefinition = "MEDIUMBLOB")
        private String photo;



        @OneToMany
        @JoinColumn(name = "admin_id", referencedColumnName = "id")
        private List<Fiche> fiche;
        @OneToMany
        @JoinColumn(name = "admin_id", referencedColumnName = "id")
        private Set<Specialite> specialites;
        @OneToMany
        @JoinColumn(name = "admin_id", referencedColumnName = "id")
        private Set<Salle> salles ;
        @OneToMany
        @JoinColumn(name = "admin_id", referencedColumnName = "id")
        private Set<UniteEnseignement> Ue ;





}
